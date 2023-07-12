package com.qs.tv.tvplayer.home

import android.app.Activity
import androidx.databinding.ObservableBoolean

class HomeRepository(private val mActivity: Activity) {

    @JvmField
    val isProgress = ObservableBoolean(true)
}