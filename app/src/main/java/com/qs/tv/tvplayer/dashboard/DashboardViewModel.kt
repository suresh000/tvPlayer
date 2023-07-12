package com.qs.tv.tvplayer.dashboard

import com.qs.tv.tvplayer.base.BaseViewModel

class DashboardViewModel(repository: DashboardRepository) : BaseViewModel() {

    val mRepository: DashboardRepository

    init {
        mRepository = repository
    }

}