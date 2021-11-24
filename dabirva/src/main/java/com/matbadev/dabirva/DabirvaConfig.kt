package com.matbadev.dabirva

import androidx.annotation.VisibleForTesting

/**
 * Global singleton configuration object for [Dabirva].
 */
object DabirvaConfig {

    /**
     * `true` if changes to the configuration are still allowed,
     * `false` otherwise.
     */
    @JvmStatic
    @Volatile
    var locked: Boolean = false
        private set

    /**
     * The [DabirvaFactory] to use for creating new instances of [Dabirva].
     *
     * This can be changed for returning custom subclasses of [Dabirva].
     *
     * By default the empty constructor of [Dabirva] is used.
     */
    @JvmStatic
    var factory: DabirvaFactory = DabirvaFactory { Dabirva() }
        set(newFactory) {
            check(!locked)
            field = newFactory
        }

    /**
     * Lock the configuration which will prevent any further modifications.
     */
    @JvmStatic
    fun lock() {
        locked = true
    }

    /**
     * Reset the configuration instance to be mutable again.
     */
    @JvmStatic
    @VisibleForTesting
    fun reset() {
        locked = false
    }

}
