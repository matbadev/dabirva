package com.matbadev.dabirva.example.ui.stickyheader.vertical

import android.os.Parcelable
import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class VerticalStickyHeaderListActivity :
    BaseActivity<Parcelable, VerticalStickyHeaderListEvent, VerticalStickyHeaderListViewModel>(
        viewModelClass = VerticalStickyHeaderListViewModel::class,
        layoutId = R.layout.activity_sticky_header_list_vertical,
    ) {

    override fun buildViewModel(repositories: AppRepositories): VerticalStickyHeaderListViewModel {
        return VerticalStickyHeaderListViewModel(
            noteRepository = repositories.noteRepository,
        )
    }

    override fun handleScreenUiEvent(event: VerticalStickyHeaderListEvent) {
    }

}
