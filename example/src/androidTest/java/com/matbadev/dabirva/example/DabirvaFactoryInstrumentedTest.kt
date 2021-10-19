package com.matbadev.dabirva.example

import android.os.Parcelable
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import com.matbadev.dabirva.example.NoteViewModels.A
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.CountingDirectExecutor
import com.matbadev.dabirva.example.util.loopMainThreadUntilIdle
import com.matbadev.dabirva.example.util.value
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DabirvaFactoryInstrumentedTest : BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
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
            Dabirva().apply {
                diffExecutor = executor
            }
        }
        scenario.recreate()

        viewModel.items.value = listOf(A)
        loopMainThreadUntilIdle()

        assertEquals(1, executor.executedCommandsCount)
    }

}
