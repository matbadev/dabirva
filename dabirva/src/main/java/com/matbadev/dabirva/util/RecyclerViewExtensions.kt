package com.matbadev.dabirva.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

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
