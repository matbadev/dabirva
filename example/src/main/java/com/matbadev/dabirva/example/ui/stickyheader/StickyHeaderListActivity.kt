package com.matbadev.dabirva.example.ui.stickyheader

import com.matbadev.dabirva.example.AppRepositories
import com.matbadev.dabirva.example.R
import com.matbadev.dabirva.example.base.BaseActivity

class StickyHeaderListActivity : BaseActivity<StickyHeaderListEvent, StickyHeaderListViewModel>(
    viewModelClass = StickyHeaderListViewModel::class,
    layoutId = R.layout.activity_sticky_header_list,
) {

    override fun buildViewModel(repositories: AppRepositories): StickyHeaderListViewModel {
        return StickyHeaderListViewModel()
    }

    override fun handleScreenUiEvent(event: StickyHeaderListEvent) {
    }

}
