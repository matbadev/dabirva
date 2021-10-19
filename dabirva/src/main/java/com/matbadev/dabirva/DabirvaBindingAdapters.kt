package com.matbadev.dabirva

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor

object DabirvaBindingAdapters {

    @JvmStatic
    @BindingAdapter(
        value = ["dabirvaItems", "dabirvaDiffExecutor"],
        requireAll = false,
    )
    fun setData(
        recyclerView: RecyclerView,
        items: List<ItemViewModel>?,
        diffExecutor: Executor?,
    ) {
        val currentAdapter: RecyclerView.Adapter<*>? = recyclerView.adapter

        // First set diff executor then items
        // to make sure diffing is done on the new diff executor.

        if (currentAdapter == null) {
            val newAdapter: Dabirva = DabirvaConfig.factory.create()
            diffExecutor?.let { newAdapter.diffExecutor = diffExecutor }
            items?.let { newAdapter.items = it }
            recyclerView.adapter = newAdapter
        } else {
            check(currentAdapter is Dabirva) { "Required an instance of Dabirva but was $currentAdapter" }
            diffExecutor?.let { currentAdapter.diffExecutor = diffExecutor }
            items?.let { currentAdapter.items = it }
        }
    }

}
