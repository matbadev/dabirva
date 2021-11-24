package com.matbadev.dabirva

/**
 * Factory creating instances of [Dabirva].
 */
fun interface DabirvaFactory {

    /**
     * Returns a new instance of [Dabirva].
     */
    fun create(): Dabirva

}
