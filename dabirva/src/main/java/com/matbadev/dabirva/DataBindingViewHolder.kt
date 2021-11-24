package com.matbadev.dabirva

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * View holder for a view which uses data binding.
 */
open class DataBindingViewHolder(
    protected val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * The currently bound [ItemViewModel].
     */
    var boundViewModel: ItemViewModel? = null
        private set

    /**
     * Binds [viewModel] to [binding] and requests a binding execution.
     */
    @MainThread
    @CallSuper
    open fun bindViewModel(viewModel: ItemViewModel) {
        if (!binding.setVariable(viewModel.bindingId, viewModel)) {
            throw UnsupportedOperationException("Variable with ID ${viewModel.bindingId} is not used in layout with ID ${viewModel.layoutId}")
        }
        binding.executePendingBindings()
        boundViewModel = viewModel
    }

    /**
     * Unbinds the currently bound view model.
     */
    @MainThread
    @CallSuper
    open fun unbind() {
        boundViewModel = null
        binding.unbind()
    }

}
