package com.matbadev.dabirva

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.internal.CommonExecutors
import com.matbadev.dabirva.internal.DiffableDiffUtilItemCallback
import java.util.concurrent.Executor

/**
 * Simple and extensible adapter for [RecyclerView]s to easily build lists
 * using the [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/).
 *
 * The view models of all items to be shown must implement [ItemViewModel].
 * Internally [ItemViewModel.layoutId] is used as item view type.
 *
 * This adapter relies on [DiffUtil] for item diffing.
 * By default diffing is done on a background thread specified by [diffExecutor].
 *
 * See the project's [README](https://github.com/matbadev/dabirva/blob/master/README.md)
 * for a detailed usage description.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class Dabirva(
    val diffExecutor: Executor = CommonExecutors.itemDiffing,
) : RecyclerView.Adapter<DataBindingViewHolder>() {

    /**
     * The current view models for the items to show in the list.
     *
     * While this can be modified manually it should usually be updated
     * using the data binding adapter defined by [DabirvaBindingAdapters.setData].
     */
    var items: List<ItemViewModel> = listOf()
        set(newItems) {
            // AsyncListDiffer already includes an optimization to check if the items are set to the same list
            // so we don't need to add this optimization here.
            field = newItems
            itemsDiffer.submitList(newItems)
        }

    private val itemsDiffer: AsyncListDiffer<Diffable> by lazy {
        val updateCallback = AdapterListUpdateCallback(this)
        val config = AsyncDifferConfig.Builder(DiffableDiffUtilItemCallback()) //
            .setBackgroundThreadExecutor(diffExecutor) //
            .build()
        AsyncListDiffer(updateCallback, config)
    }

    final override fun getItemCount(): Int {
        return items.size
    }

    final override fun getItemViewType(position: Int): Int {
        val item: ItemViewModel = items[position]
        return item.layoutId
    }

    // Final overwrite as stable IDs are not required when using DiffUtil.
    // See: https://stackoverflow.com/a/62281250/
    final override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(false)
    }

    // Final overwrite as stable IDs are not required when using DiffUtil.
    // See: https://stackoverflow.com/a/62281250/
    final override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DataBindingViewHolder {
        // Dabirva uses the layout ID as item view type.
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    @CallSuper
    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val item: ItemViewModel = items[position]
        holder.bindViewModel(item)
    }

    // Final overwrite as payloads are not required when using data binding.
    final override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    @CallSuper
    override fun onViewRecycled(holder: DataBindingViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun toString(): String {
        return "Dabirva(items=$items, diffExecutor=$diffExecutor)"
    }

}
