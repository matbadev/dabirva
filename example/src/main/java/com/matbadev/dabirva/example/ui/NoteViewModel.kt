package com.matbadev.dabirva.example.ui

import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class NoteViewModel(
    val id: Long,
    val text: String,
) : ItemViewModel {

    override val bindingId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.item_note

    override fun entityEquals(other: Any?): Boolean {
        return other is NoteViewModel && id == other.id
    }

}
