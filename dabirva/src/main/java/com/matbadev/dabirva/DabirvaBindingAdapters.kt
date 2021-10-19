package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object DabirvaBindingAdapters {

    @JvmStatic
    @BindingAdapter(
        value = ["dabirvaItems"],
        requireAll = false,
    )
    fun setData(
        recyclerView: RecyclerView,
        items: List<ItemViewModel>?,
    ) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter

        if (currentAdapter == null) {
            val newAdapter: Dabirva = DabirvaConfig.factory.create()
            items?.let { newAdapter.items = it }
            recyclerView.adapter = newAdapter
        } else {
            check(currentAdapter is Dabirva) { "Required an instance of Dabirva but was $currentAdapter" }
            items?.let { currentAdapter.items = it }
        }
    }

}
