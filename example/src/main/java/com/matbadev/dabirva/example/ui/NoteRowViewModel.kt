package com.matbadev.dabirva.example.ui

import androidx.annotation.ColorInt
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class NoteRowViewModel(
    val id: Long,
    val text: String,
    @ColorInt val color: Int,
) : ItemViewModel {

    override val bindingId: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.item_note_row

    override fun entityEquals(other: Any?): Boolean {
        return other is NoteRowViewModel && other.id == id
    }

}
