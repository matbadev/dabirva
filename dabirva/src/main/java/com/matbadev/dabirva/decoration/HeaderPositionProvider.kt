package com.matbadev.dabirva.decoration

import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.ItemViewModel

/**
 * Provides the position of the header for a specific item.
 */
interface HeaderPositionProvider {

    /**
     * Returns the position in [items] of the header to use for the item at [itemPosition].
     * If there is no header, [RecyclerView.NO_POSITION] should be returned.
     */
    fun getHeaderPositionForItem(itemPosition: Int, items: List<ItemViewModel>): Int

}
