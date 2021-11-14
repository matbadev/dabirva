package com.matbadev.dabirva.util

import androidx.databinding.ObservableField

inline var <T> ObservableField<T>.value
    get() = this.get()
    set(value) = this.set(value)
