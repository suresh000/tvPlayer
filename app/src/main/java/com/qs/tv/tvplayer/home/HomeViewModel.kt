package com.qs.tv.tvplayer.home

import com.qs.tv.tvplayer.base.BaseViewModel

class HomeViewModel(repository: HomeRepository) : BaseViewModel() {

    val mRepository: HomeRepository

    init {
        mRepository = repository
    }
}