package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides data binding adapters for working with [Dabirva].
 */
object DabirvaBindingAdapters {

    @JvmStatic
    @BindingAdapter("dabirvaItems")
    fun setData(recyclerView: RecyclerView, dabirvaItems: List<ItemViewModel>?) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (currentAdapter == null) {
            val newAdapter: Dabirva = DabirvaConfig.factory.create()
            dabirvaItems?.let { newAdapter.updateItems(it) }
            recyclerView.adapter = newAdapter
        } else {
            check(currentAdapter is Dabirva) { "Required an instance of Dabirva but was $currentAdapter" }
            dabirvaItems?.let { currentAdapter.updateItems(it) }
        }
    }

}
