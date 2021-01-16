package com.matbadev.dabirva.example.ui

import androidx.annotation.ColorInt
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.BR
import com.matbadev.dabirva.example.R

data class NoteRowViewModel(
    override val id: Long,
    val text: String,
    @ColorInt val color: Int,
) : ItemViewModel {

    override val layoutId: Int = R.layout.item_note_row

    override val bindingId: Int = BR.viewModel

}