package com.qs.tv.tvplayer.home

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.databinding.ObservableField
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.AppNavigator
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.room.entity.PlayerItem
import java.io.File

@UnstableApi
class PlayerItemViewModel(private val mContext: Context,
                          item: PlayerItem) : ViewModelItem {

    @JvmField
    val mItem = ObservableField<PlayerItem>()

    @JvmField
    val imageUri = ObservableField<Uri>()

    @JvmField
    val imageBitmap = ObservableField<Bitmap>()

    init {
        mItem.set(item)

        if (item.isVideo) {
            val bitmap = ThumbnailUtils.createVideoThumbnail(item.storageThumbnailPath, MediaStore.Images.Thumbnails.MINI_KIND)
            imageBitmap.set(bitmap)
        } else {
            imageUri.set(Uri.fromFile(File(item.storageThumbnailPath)))
        }
    }

    fun itemClick(view: View) {
        val video = mItem.get()
        if (video != null) {
            AppNavigator.navigateToExoPlayerActivity(view.context, video.id)
        }
    }

}