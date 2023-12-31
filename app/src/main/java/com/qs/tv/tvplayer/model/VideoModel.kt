package com.qs.tv.tvplayer.model

import android.graphics.Bitmap
import android.net.Uri
import java.io.Serializable

data class VideoModel (
    var id: String = "",
    var name: String = "",
    var path: String = "",
    var type: String = "",
    var uri: Uri,
    var imageBitmap: Bitmap? = null,
    var isVideo: Boolean = false
) : Serializable