package com.qs.tv.tvplayer.player.exoplayer

import com.qs.tv.tvplayer.base.BaseViewModel

class ExoPlayerViewModel(repository: ExoPlayerRepository) : BaseViewModel() {

    val mRepository: ExoPlayerRepository

    init {
        mRepository = repository
    }
}