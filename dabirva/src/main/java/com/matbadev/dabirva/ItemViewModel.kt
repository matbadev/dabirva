package com.matbadev.dabirva

/**
 * Contract for data classes to be used as [Dabirva.items].
 *
 * This combines the interfaces [BindableLayout] and [Diffable].
 *
 * It is recommended to implement inherited classes as Kotlin data classes
 * to benefit from a default [equals] and [hashCode] implementation.
 * Event listeners should **not** be declared in the data class constructor
 * but instead as non-final properties (see [Diffable.equals]).
 */
interface ItemViewModel : BindableLayout, Diffable
