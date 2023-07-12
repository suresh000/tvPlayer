package com.qs.tv.tvplayer.home

import androidx.databinding.ObservableField
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.model.VideoModel

class VideoItemViewModel(video: VideoModel) : ViewModelItem {

    @JvmField
    val mVideo = ObservableField<VideoModel>()

    init {
        mVideo.set(video)
    }

}