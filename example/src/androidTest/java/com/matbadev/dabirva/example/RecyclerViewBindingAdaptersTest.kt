package com.matbadev.dabirva.example

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.TestDecoration
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.example.util.useActivity
import com.matbadev.dabirva.util.decorations
import com.matbadev.dabirva.util.value
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecyclerViewBindingAdaptersTest :
    BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
        activityClass = TestActivity::class,
    ) {

    private lateinit var recyclerView: RecyclerView

    private var layoutChangesCount = 0

    @Before
    fun initView() {
        recyclerView = scenario.useActivity { it.findViewById(R.id.recycler_view) }
        recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            layoutChangesCount++
        }
    }

    @Test
    fun nullDecorations() {
        viewModel.itemDecorations.value = null
        loopMainThreadUntilIdle()

        assertEquals(null, viewModel.itemDecorations.value)
        assertTrue(recyclerView.decorations.isEmpty())
        assertEquals(0, layoutChangesCount)
    }

    @Test
    fun singleDecoration() {
        val decorations = listOf(TestDecoration(1))

        viewModel.itemDecorations.value = decorations
        loopMainThreadUntilIdle()

        assertEquals(decorations, viewModel.itemDecorations.value)
        assertEquals(decorations, recyclerView.decorations)
        assertEquals(1, layoutChangesCount)
    }

    @Test
    fun multipleDecorations() {
        val decorations = listOf(
            TestDecoration(1),
            TestDecoration(2),
            TestDecoration(3),
        )

        viewModel.itemDecorations.value = decorations
        loopMainThreadUntilIdle()

        assertEquals(decorations, viewModel.itemDecorations.value)
        assertEquals(decorations, recyclerView.decorations)
        assertEquals(1, layoutChangesCount)
    }

    @Test
    fun decorationsDiffingEqual() {
        val decorations = listOf(TestDecoration(1), TestDecoration(2))
        val decorationsFlatCopy = decorations.toList()
        val equalDecorations = listOf(TestDecoration(1), TestDecoration(2))

        viewModel.itemDecorations.value = decorations
        loopMainThreadUntilIdle()

        assertEquals(decorations, viewModel.itemDecorations.value)
        assertEquals(decorations, recyclerView.decorations)
        assertEquals(1, layoutChangesCount)

        // The following should not trigger a layout change as the decorations are identical.
        viewModel.itemDecorations.value = decorationsFlatCopy
        loopMainThreadUntilIdle()

        // The following should not trigger a layout change as the decorations are equal.
        viewModel.itemDecorations.value = equalDecorations
        loopMainThreadUntilIdle()

        assertEquals(decorations, viewModel.itemDecorations.value)
        assertEquals(decorations, recyclerView.decorations)
        assertEquals(1, layoutChangesCount)
    }

    @Test
    fun decorationsDiffingNotEqual() {
        val initialDecorations = listOf(TestDecoration(1), TestDecoration(2))
        val updatedDecorations = listOf(TestDecoration(1), TestDecoration(3))

        viewModel.itemDecorations.value = initialDecorations
        loopMainThreadUntilIdle()

        assertEquals(initialDecorations, viewModel.itemDecorations.value)
        assertEquals(initialDecorations, recyclerView.decorations)
        assertEquals(1, layoutChangesCount)

        // The following should trigger a layout change as the decorations are not equal.
        viewModel.itemDecorations.value = updatedDecorations
        loopMainThreadUntilIdle()

        assertEquals(updatedDecorations, viewModel.itemDecorations.value)
        assertEquals(updatedDecorations, recyclerView.decorations)
        assertEquals(2, layoutChangesCount)
    }

}
