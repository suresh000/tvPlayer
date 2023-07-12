package com.qs.tv.tvplayer.model

import android.graphics.Bitmap

data class VideoModel (
    var name: String = "",
    var path: String = "",
    var thumbImage: Bitmap? = null
)