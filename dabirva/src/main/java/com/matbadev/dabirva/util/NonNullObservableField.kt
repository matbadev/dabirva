package com.matbadev.dabirva.util

import androidx.databinding.ObservableField

/**
 * Implementation of [ObservableField] which only allows non-null values.
 *
 * @see [com.matbadev.dabirva.util.value]
 */
class NonNullObservableField<T : Any>(
    initialValue: T,
) : ObservableField<T>(initialValue) {

    override fun get(): T {
        return super.get() ?: throw AssertionError("ObservableField.get() returned null although initial value was set")
    }

    override fun set(value: T) {
        super.set(value)
    }

}
