package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.data.NotePriority

data class HeaderRowViewModel(
    val priority: NotePriority,
    val text: String,
) : ItemViewModel {

    override val bindingId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.item_header_row

    override fun entityEquals(other: Any?): Boolean {
        return other is HeaderRowViewModel && priority == other.priority
    }

}
