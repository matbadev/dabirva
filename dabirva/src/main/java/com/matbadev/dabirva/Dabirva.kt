package com.matbadev.dabirva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.internal.DiffableDiffUtilItemCallback
import com.matbadev.dabirva.util.DirectExecutor
import java.util.concurrent.Executor

open class Dabirva : RecyclerView.Adapter<DataBindingViewHolder>() {

    var items: List<ItemViewModel> = listOf()
        set(newItems) {
            field = newItems
            itemsDiffer.submitList(newItems)
        }

    var diffExecutor: Executor = DirectExecutor()
        set(newDiffExecutor) {
            val oldDiffExecutor = field
            field = newDiffExecutor
            if (oldDiffExecutor != newDiffExecutor) {
                itemsDiffer = buildItemsDiffer()
            }
        }

    private var itemsDiffer: AsyncListDiffer<Diffable> = buildItemsDiffer()

    final override fun getItemCount(): Int {
        return items.size
    }

    final override fun getItemViewType(position: Int): Int {
        val item: ItemViewModel = items[position]
        return item.layoutId
    }

    /**
     * Stable IDs are not required when using DiffUtil.
     * See: [https://stackoverflow.com/a/62281250/]
     */
    final override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(false)
    }

    /**
     * Stable IDs are not required when using DiffUtil.
     * See: [https://stackoverflow.com/a/62281250/]
     */
    final override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    @CallSuper
    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item: ItemViewModel = items[position]
        holder.bindViewModel(item)
    }

    /**
     * Payloads are not required as diffing is done using data binding.
     */
    final override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    @CallSuper
    override fun onViewRecycled(holder: DataBindingViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    private fun buildItemsDiffer(): AsyncListDiffer<Diffable> {
        val config = AsyncDifferConfig.Builder(DiffableDiffUtilItemCallback()) //
            .setBackgroundThreadExecutor(diffExecutor).build()
        return AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    override fun toString(): String {
        return "Dabirva(items=$items, diffExecutor=$diffExecutor)"
    }

}
