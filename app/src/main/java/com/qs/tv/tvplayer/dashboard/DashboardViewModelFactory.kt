package com.qs.tv.tvplayer.dashboard

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DashboardViewModelFactory(private val mRepository: DashboardRepository) : AbstractSavedStateViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return DashboardViewModel(mRepository) as T
    }

}