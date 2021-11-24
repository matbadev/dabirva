package com.matbadev.dabirva

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Contract for everything necessary to bind a data object to a layout
 * using the [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/).
 *
 * The [layoutId] must specify a layout which defines a variable with a name that corresponds to [bindingId].
 *
 * In the context of [Dabirva] the [bindingId] is usually `BR.viewModel`
 * and the layout defines the corresponding view model variable, e.g.:
 *
 * ```xml
 * <layout xmlns:android="http://schemas.android.com/apk/res/android">
 *   <data>
 *     <variable
 *       name="viewModel"
 *       type="com.matbadev.dabirva.example.NoteViewModel" />
 *   </data>
 *
 *   <!-- View hierarchy -->
 *
 * </layout>
 * ```
 */
interface BindableLayout {

    /**
     * Returns the ID of the layout stored in the generated `R` class.
     *
     * [Dabirva] uses this as item view type and to inflate the specific layout.
     *
     * @see DataBindingUtil.inflate
     */
    val layoutId: Int

    /**
     * Returns the binding ID stored in the generated `BR` class.
     *
     * This is used to bind a data object to the layout with [layoutId].
     * Therefore that layout must define a variable with a name that corresponds to [bindingId].
     *
     * @see ViewDataBinding.setVariable
     */
    val bindingId: Int

}
