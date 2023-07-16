package com.matbadev.dabirva.example

import android.os.Parcelable
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import com.matbadev.dabirva.example.NoteViewModels.A
import com.matbadev.dabirva.example.NoteViewModels.B
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.CountingDirectExecutor
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.util.value
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DabirvaFactoryInstrumentedTest :
    BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
        activityClass = TestActivity::class,
    ) {

    @Before
    fun reset() {
        DabirvaConfig.factory = DabirvaFactory { Dabirva() }
    }

    @Test
    fun overwriteDefaultDiffExecutor() {
        val executor = CountingDirectExecutor()
        DabirvaConfig.factory = DabirvaFactory {
            Dabirva(executor)
        }
        scenario.recreate()

        // First insert is done synchronously.
        viewModel.items.value = listOf(A)
        loopMainThreadUntilIdle()

        viewModel.items.value = listOf(B)
        loopMainThreadUntilIdle()

        assertEquals(1, executor.executedCommandsCount)
    }

}
