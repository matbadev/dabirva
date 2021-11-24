package com.matbadev.dabirva.decoration

import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.ItemViewModel

/**
 * Provides the position of the next header to use given a list of [ItemViewModel]s.
 */
interface HeaderPositionProvider {

    /**
     * Returns the position in [items] of the next header after [itemPosition].
     * If there is no header [RecyclerView.NO_POSITION] should be returned.
     */
    fun getHeaderPositionForItem(itemPosition: Int, items: List<ItemViewModel>): Int

}
