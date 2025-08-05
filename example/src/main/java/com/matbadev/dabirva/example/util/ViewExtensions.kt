package com.matbadev.dabirva.example.util

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.applyAndConsumeWindowInsets() {
    ViewGroupCompat.installCompatInsetsDispatch(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(insets.left, insets.top, insets.right, insets.bottom)
        WindowInsetsCompat.CONSUMED
    }
}
