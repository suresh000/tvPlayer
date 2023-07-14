package com.qs.tv.tvplayer.player.exoplayer

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ExoPlayerViewModelFactory(private val mRepository: ExoPlayerRepository) : AbstractSavedStateViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return ExoPlayerViewModel(mRepository) as T
    }

}