package com.matbadev.dabirva.example

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
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
import com.matbadev.dabirva.example.NoteViewModels.D
import com.matbadev.dabirva.example.NoteViewModels.E
import com.matbadev.dabirva.example.ui.NoteViewModel
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.CountingDirectExecutor
import com.matbadev.dabirva.example.util.atViewPosition
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.example.util.useActivity
import com.matbadev.dabirva.example.util.withChildCount
import com.matbadev.dabirva.util.value
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class ItemDiffingInstrumentedTest :
    BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
        activityClass = TestActivity::class,
    ) {

    enum class DiffExecutorMode {
        SYNC,
        ASYNC
    }

    @Mock
    private lateinit var adapterDataObserver: AdapterDataObserver

    @Before
    fun resetConfig() {
        DabirvaConfig.reset()
    }

    @Test
    fun insertSingleSync() {
        insertSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun insertSingleAsync() {
        insertSingle(DiffExecutorMode.ASYNC)
    }

    private fun insertSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, B, C),
            updatedItems = listOf(A, B, D, C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeInserted(2, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun insertMultipleSync() {
        insertMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun insertMultipleAsync() {
        insertMultiple(DiffExecutorMode.ASYNC)
    }

    private fun insertMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, B, D),
            updatedItems = listOf(A, B, C, D, E),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeInserted(3, 1)
            verify(adapterDataObserver).onItemRangeInserted(2, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun removeSingleSync() {
        removeSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun removeSingleAsync() {
        removeSingle(DiffExecutorMode.ASYNC)
    }

    private fun removeSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, B, C),
            updatedItems = listOf(A, C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeRemoved(1, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun removeMultipleSync() {
        removeMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun removeMultipleAsync() {
        removeMultiple(DiffExecutorMode.ASYNC)
    }

    private fun removeMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, B, C),
            updatedItems = listOf(B),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeRemoved(2, 1)
            verify(adapterDataObserver).onItemRangeRemoved(0, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun changeSingleSync() {
        changeSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun changeSingleAsync() {
        changeSingle(DiffExecutorMode.ASYNC)
    }

    private fun changeSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, NoteViewModel(2, "initial"), C),
            updatedItems = listOf(A, NoteViewModel(2, "updated"), C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeChanged(1, 1, null)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun changeMultipleSync() {
        changeMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun changeMultipleAsync() {
        changeMultiple(DiffExecutorMode.ASYNC)
    }

    private fun changeMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, NoteViewModel(2, "initial"), C, NoteViewModel(4, "initial")),
            updatedItems = listOf(A, NoteViewModel(2, "updated"), C, NoteViewModel(4, "updated")),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            verify(adapterDataObserver).onItemRangeChanged(1, 1, null)
            verify(adapterDataObserver).onItemRangeChanged(3, 1, null)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun moveSingleSync() {
        moveSingle(DiffExecutorMode.SYNC)
    }

    @Test
    fun moveSingleAsync() {
        moveSingle(DiffExecutorMode.ASYNC)
    }

    private fun moveSingle(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, B, C),
            updatedItems = listOf(B, A, C),
            diffExecutorMode = diffExecutorMode,
        )
        verify(adapterDataObserver).onItemRangeMoved(0, 1, 1)
        verifyNoMoreInteractions(adapterDataObserver)
    }

    @Test
    fun moveMultipleSync() {
        moveMultiple(DiffExecutorMode.SYNC)
    }

    @Test
    fun moveMultipleAsync() {
        moveMultiple(DiffExecutorMode.ASYNC)
    }

    private fun moveMultiple(diffExecutorMode: DiffExecutorMode) {
        runTest(
            initialItems = listOf(A, B, C, D),
            updatedItems = listOf(D, A, C, B),
            diffExecutorMode = diffExecutorMode,
        )
        inOrder(adapterDataObserver).apply {
            // A  B  C  D
            //    |>>|     (first call)
            // A  C  B  D
            // |<<<<<<<<|  (second call)
            // D  A  C  B
            verify(adapterDataObserver).onItemRangeMoved(1, 2, 1)
            verify(adapterDataObserver).onItemRangeMoved(3, 0, 1)
        }
        verifyNoMoreInteractions(adapterDataObserver)
    }

    private fun runTest(
        initialItems: List<NoteViewModel>,
        updatedItems: List<NoteViewModel>,
        diffExecutorMode: DiffExecutorMode,
    ) = when (diffExecutorMode) {
        DiffExecutorMode.SYNC -> runTestSync(initialItems, updatedItems)
        DiffExecutorMode.ASYNC -> runTestAsync(initialItems, updatedItems)
    }

    private fun runTestSync(initialItems: List<NoteViewModel>, updatedItems: List<NoteViewModel>) {
        val recyclerView: RecyclerView = scenario.useActivity { it.findViewById(R.id.recycler_view) }
        recyclerView.itemAnimator = null

        viewModel.items.value = initialItems
        checkRecyclerViewItems(initialItems)

        val recyclerViewAdapter = checkNotNull(recyclerView.adapter)
        recyclerViewAdapter.registerAdapterDataObserver(adapterDataObserver)
        try {
            viewModel.items.value = updatedItems
            checkRecyclerViewItems(updatedItems)
        } finally {
            recyclerViewAdapter.unregisterAdapterDataObserver(adapterDataObserver)
        }
    }

    private fun runTestAsync(
        initialItems: List<NoteViewModel>,
        updatedItems: List<NoteViewModel>,
    ) {
        val diffExecutor = CountingDirectExecutor()
        DabirvaConfig.factory = DabirvaFactory { Dabirva(diffExecutor) }

        // Force activity recreation to make sure new DabirvaFactory is used.
        scenario.recreate()

        val recyclerView: RecyclerView = scenario.useActivity { it.findViewById(R.id.recycler_view) }
        recyclerView.itemAnimator = null

        // First insert is done synchronously.
        viewModel.items.value = initialItems
        checkRecyclerViewItems(initialItems)

        val recyclerViewAdapter = checkNotNull(recyclerView.adapter)
        recyclerViewAdapter.registerAdapterDataObserver(adapterDataObserver)
        try {
            assertEquals(0, diffExecutor.executedCommandsCount)
            viewModel.items.value = updatedItems
            checkRecyclerViewItems(updatedItems) // Executes the item diffing.
            assertEquals(1, diffExecutor.executedCommandsCount)
        } finally {
            recyclerViewAdapter.unregisterAdapterDataObserver(adapterDataObserver)
        }
    }

    private fun checkRecyclerViewItems(expectedItems: List<NoteViewModel>) {
        loopMainThreadUntilIdle()
        onView(withId(R.id.recycler_view)) //
            .check(matches(withChildCount(expectedItems.size)))
        expectedItems.forEachIndexed { index, noteViewModel ->
            onView(atViewPosition(R.id.recycler_view, index)) //
                .check(matches(withText(noteViewModel.text)))
        }
    }

}
