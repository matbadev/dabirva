package com.matbadev.dabirva.example.util

import androidx.recyclerview.widget.RecyclerView

class TestDecoration(
    val id: Long,
) : RecyclerView.ItemDecoration() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TestDecoration) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
