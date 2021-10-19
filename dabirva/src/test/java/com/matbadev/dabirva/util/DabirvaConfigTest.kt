package com.matbadev.dabirva.util

import com.matbadev.dabirva.Dabirva
import com.matbadev.dabirva.DabirvaConfig
import com.matbadev.dabirva.DabirvaFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DabirvaConfigTest {

    @BeforeEach
    fun resetConfig() {
        DabirvaConfig.reset()
    }

    @Nested
    inner class Locking {

        @Test
        fun `GIVEN config not changed EXPECT not locked`() {
            assertFalse(DabirvaConfig.locked)
        }

        @Test
        fun `GIVEN config not changed WHEN update config EXPECT config updated`() {
            val factory = DabirvaFactory { Dabirva() }

            DabirvaConfig.factory = factory

            assertEquals(factory, DabirvaConfig.factory)
        }

        @Test
        fun `GIVEN config locked WHEN update config EXPECT exception`() {
            val factory = DabirvaFactory { Dabirva() }
            DabirvaConfig.lock()

            assertThrows<IllegalStateException> {
                DabirvaConfig.factory = factory
            }
        }

    }

    @Nested
    inner class Factory {

        @Test
        fun `WHEN update factory EXPECT new factory applied`() {
            val factory = DabirvaFactory { Dabirva() }

            DabirvaConfig.factory = factory

            assertEquals(factory, DabirvaConfig.factory)
        }

    }

}
