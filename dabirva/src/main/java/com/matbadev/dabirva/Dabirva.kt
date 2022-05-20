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
     * The list returned by the getter is always unmodifiable.
     *
     * The setter is deprecated to avoid confusion, consider using [updateItems] instead.
     */
    var items: List<ItemViewModel>
        // When setting this property to new items
        // the getter must only return those new items AFTER the diff results were applied to this adapter.
        // Otherwise inconsistencies might occur,
        // e.g. when an item decoration queries the new items using this property before the diffing is completed.
        // This problem is solved here by not adding a backend field for this property
        // but instead using the list kept in the differ as single source of truth.
        get() = itemsDiffer.currentList
        @Deprecated(
            "Use updateItems() instead. The list passed to this setter might not immediately be reflected by the getter which might lead to confusion."
        )
        set(newItems) {
            updateItems(newItems)
        }

    private val itemsDiffer: AsyncListDiffer<ItemViewModel> = run {
        val updateCallback = AdapterListUpdateCallback(this)
        val diffItemCallback = DiffableDiffUtilItemCallback<ItemViewModel>()
        val config = AsyncDifferConfig.Builder(diffItemCallback)
            .setBackgroundThreadExecutor(diffExecutor)
            .build()
        AsyncListDiffer(updateCallback, config)
    }

    /**
     * Update the items in this adapter to [newItems].
     *
     * This method performs an item diffing between the current items and [newItems].
     * That diffing might be done in a background thread using [diffExecutor]
     * so [items] might not immediately reflect the new list.
     *
     * With [onItemsApplied] an optional callback can be supplied
     * to be notified once the items have been applied
     * (and are reflected by [items]).
     *
     * **Usually this method should not be called directly.
     * Instead the items should be updated using the data binding adapter defined by [DabirvaBindingAdapters.setData].**
     */
    fun updateItems(newItems: List<ItemViewModel>, onItemsApplied: (() -> Unit)? = null) {
        itemsDiffer.submitList(newItems, onItemsApplied)
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
