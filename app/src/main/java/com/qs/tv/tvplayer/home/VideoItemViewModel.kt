package com.qs.tv.tvplayer.home

import android.view.View
import androidx.databinding.ObservableField
import com.qs.tv.tvplayer.AppNavigator
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.model.VideoModel

class VideoItemViewModel(video: VideoModel) : ViewModelItem {

    @JvmField
    val mVideo = ObservableField<VideoModel>()

    init {
        mVideo.set(video)
    }

    fun itemClick(view: View) {
        val video = mVideo.get()
        if (video != null) {
            AppNavigator.navigateToPlayerActivity(view.context, video.id)
        }
    }

}