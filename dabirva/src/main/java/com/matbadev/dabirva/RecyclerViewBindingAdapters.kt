package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.matbadev.dabirva.util.decorations

/**
 * Provides general data binding adapters to bind to [RecyclerView]s.
 */
object RecyclerViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("itemDecorations")
    fun setItemDecorations(recyclerView: RecyclerView, itemDecorations: List<ItemDecoration>?) {
        recyclerView.decorations = itemDecorations ?: listOf()
    }

}
