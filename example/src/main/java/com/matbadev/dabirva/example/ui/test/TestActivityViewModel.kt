package com.matbadev.dabirva.example.ui.test

import android.os.Parcelable
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.matbadev.dabirva.ItemViewModel
import com.matbadev.dabirva.example.base.BaseScreenViewModel
import java.util.concurrent.Executor

class TestActivityViewModel : BaseScreenViewModel<Parcelable, TestActivityEvent>() {

    val items = ObservableField<List<ItemViewModel>?>()

    val itemDecorations = ObservableField<List<RecyclerView.ItemDecoration>?>()

    val diffExecutor = ObservableField<Executor?>()

}
