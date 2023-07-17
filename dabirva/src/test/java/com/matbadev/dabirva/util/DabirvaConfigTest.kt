package com.matbadev.dabirva.util

import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class DabirvaConfigTest {

    @Before
    fun resetConfig() {
        DabirvaConfig.reset()
    }

    @Test
    fun `GIVEN config not changed EXPECT not locked`() {
        assertFalse(DabirvaConfig.locked)
    }

    @Test
    fun `GIVEN config locked WHEN update config EXPECT exception`() {
        val factory = DabirvaFactory { Dabirva() }
        DabirvaConfig.lock()

        assertFailsWith<IllegalStateException> {
            DabirvaConfig.factory = factory
        }
    }

    @Test
    fun `WHEN update factory EXPECT new factory applied`() {
        val factory = DabirvaFactory { Dabirva() }

        DabirvaConfig.factory = factory

        assertEquals(factory, DabirvaConfig.factory)
    }

}
