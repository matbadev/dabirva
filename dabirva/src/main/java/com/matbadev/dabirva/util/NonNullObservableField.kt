package com.matbadev.dabirva.util

import androidx.databinding.ObservableField

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
