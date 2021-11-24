package com.matbadev.dabirva.util

import androidx.databinding.ObservableField

/**
 * Exposes the value stored in an [ObservableField] as a property.
 *
 * As this is an inline property the resulting code will be equivalent
 * to calling [ObservableField.get] and [ObservableField.set] directly.
 *
 * @see [NonNullObservableField]
 */
inline var <T> ObservableField<T>.value
    get() = this.get()
    set(value) = this.set(value)
