package com.matbadev.dabirva.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Exposes the decorations set for a [RecyclerView] as a property.
 *
 * For performance reasons the setter only updates the decorations
 * when the new decorations are not equal to the old ones.
 */
var RecyclerView.decorations: List<ItemDecoration>
    get() {
        val decorationsCount: Int = itemDecorationCount
        val decorations = mutableListOf<ItemDecoration>()
        for (decorationIndex in 0 until decorationsCount) {
            decorations += getItemDecorationAt(decorationIndex)
        }
        return decorations
    }
    set(newDecorations) {
        val existingDecorations: List<ItemDecoration> = decorations
        if (existingDecorations != newDecorations) {
            existingDecorations.forEach { decoration: ItemDecoration ->
                removeItemDecoration(decoration)
            }
            newDecorations.forEach { decoration: ItemDecoration ->
                addItemDecoration(decoration)
            }
        }
    }
