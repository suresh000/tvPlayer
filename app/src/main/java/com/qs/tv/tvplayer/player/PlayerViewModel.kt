package com.qs.tv.tvplayer.player

import com.qs.tv.tvplayer.base.BaseViewModel

class PlayerViewModel(repository: PlayerRepository) : BaseViewModel() {

    val mRepository: PlayerRepository

    init {
        mRepository = repository
    }
}