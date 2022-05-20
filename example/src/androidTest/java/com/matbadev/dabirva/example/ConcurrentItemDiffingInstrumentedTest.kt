package com.matbadev.dabirva.example

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import com.matbadev.dabirva.example.NoteViewModels.A
import com.matbadev.dabirva.example.NoteViewModels.B
import com.matbadev.dabirva.example.NoteViewModels.C
import com.matbadev.dabirva.example.ui.NoteViewModel
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.QueueExecutor
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.example.util.withChildCount
import com.matbadev.dabirva.util.value
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * This test makes sure that the item list kept by the adapter is consistent
 * when modified after an item diffing performed on a background thread.
 *
 * This prevents exceptions caused by inconsistencies, e.g.:
 *
 * ```
 * java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionDataBindingViewHolder{7c0fb5e position=3 id=-1, oldPos=0, pLpos:0 scrap [attachedScrap] tmpDetached no parent} androidx.recyclerview.widget.RecyclerView{d76ec5b VFED..... ......I. 0,0-1080,1911 #7f08007a app:id/recycler_view}, adapter:Dabirva(items=[NoteViewModel(id=1, text=A), NoteViewModel(id=2, text=B), NoteViewModel(id=3, text=C)], diffExecutor=com.matbadev.dabirva.example.util.QueueExecutor@1088cf8), layout:androidx.recyclerview.widget.LinearLayoutManager@b67c5d1, context:com.matbadev.dabirva.example.ui.test.TestActivity@cc40c75
 * at androidx.recyclerview.widget.RecyclerView$Recycler.validateViewHolderForOffsetPosition(RecyclerView.java:6156)
 * at androidx.recyclerview.widget.RecyclerView$Recycler.tryGetViewHolderForPositionByDeadline(RecyclerView.java:6339)
 * at androidx.recyclerview.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:6300)
 * at androidx.recyclerview.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:6296)
 * at androidx.recyclerview.widget.LinearLayoutManager$LayoutState.next(LinearLayoutManager.java:2330)
 * at androidx.recyclerview.widget.LinearLayoutManager.layoutChunk(LinearLayoutManager.java:1631)
 * at androidx.recyclerview.widget.LinearLayoutManager.fill(LinearLayoutManager.java:1591)
 * at androidx.recyclerview.widget.LinearLayoutManager.onLayoutChildren(LinearLayoutManager.java:668)
 * at androidx.recyclerview.widget.RecyclerView.dispatchLayoutStep1(RecyclerView.java:4255)
 * at androidx.recyclerview.widget.RecyclerView.dispatchLayout(RecyclerView.java:4010)
 * at androidx.recyclerview.widget.RecyclerView.onLayout(RecyclerView.java:4578)
 * at android.view.View.layout(View.java:23143)
 * at android.view.ViewGroup.layout(ViewGroup.java:6412)
 * [...]
 * ```
 */
class ConcurrentItemDiffingInstrumentedTest :
    BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
        activityClass = TestActivity::class,
    ) {

    @Before
    fun resetConfig() {
        DabirvaConfig.reset()
    }

    @Test
    fun basic() {
        val updatedItems: List<NoteViewModel> = listOf(A, B, C)

        val diffExecutor = QueueExecutor()
        DabirvaConfig.factory = DabirvaFactory { Dabirva(diffExecutor) }

        // Force activity recreation to make sure new DabirvaFactory is used.
        scenario.recreate()

        scenario.onActivity { activity ->
            // First insert is done synchronously.
            viewModel.items.value = listOf()
        }

        loopMainThreadUntilIdle()
        assertEquals(0, diffExecutor.pendingTasksCount)

        scenario.onActivity { activity ->
            viewModel.items.value = updatedItems
            val recyclerView: RecyclerView = activity.findViewById(R.id.recycler_view)

            // Force RecyclerView to re-query displayed items.
            recyclerView.adapter = recyclerView.adapter
        }

        loopMainThreadUntilIdle()
        assertEquals(1, diffExecutor.pendingTasksCount)

        diffExecutor.executePendingTasks()

        loopMainThreadUntilIdle()
        checkRecyclerViewItems(updatedItems)
    }

    private fun checkRecyclerViewItems(expectedItems: List<NoteViewModel>) {
        onView(withId(R.id.recycler_view))
            .check(matches(withChildCount(expectedItems.size)))
        expectedItems.forEachIndexed { index, noteViewModel ->
            onView(atViewPosition(R.id.recycler_view, index))
                .check(matches(withText(noteViewModel.text)))
        }
    }

}
