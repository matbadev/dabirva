package com.matbadev.dabirva.example

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.example.ui.test.TestActivity
import com.matbadev.dabirva.example.ui.test.TestActivityEvent
import com.matbadev.dabirva.example.ui.test.TestActivityViewModel
import com.matbadev.dabirva.example.util.useActivity
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BasicInstrumentedTest : BaseInstrumentedTest<Parcelable, TestActivityEvent, TestActivityViewModel, TestActivity>(
    activityClass = TestActivity::class,
) {

    @Test
    fun defaultDabirva() {
        val recyclerView: RecyclerView = scenario.useActivity { it.findViewById(R.id.recycler_view) }
        val adapter: RecyclerView.Adapter<*>? = recyclerView.adapter

        assertTrue(adapter is Dabirva)
        assertFalse(adapter.hasStableIds())
        assertTrue(adapter.items.isEmpty())
    }

}
