package com.qs.tv.tvplayer.utils

import android.content.ContentResolver
import android.content.Context
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.home.VideoItemViewModel
import com.qs.tv.tvplayer.model.VideoModel
import com.qs.tv.tvplayer.utils.helpers.ExceptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class VideoUtil private constructor() {

    companion object {

        @JvmStatic
        fun getVideoList(context: Context, callback: (List<ViewModelItem>) -> Unit) {
            val viewModels = ArrayList<ViewModelItem>()

            val contentResolver: ContentResolver = context.contentResolver
            val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DURATION)
            CoroutineScope(Dispatchers.IO).launch {
                contentResolver.query(uri, projection, null, null,
                    MediaStore.Video.Media.DATE_ADDED + " DESC")?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        do {
                            val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                            val videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                            val videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                            val videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)

                            try {
                                val file = File(videoPath)

                                val videoModel = VideoModel(
                                    id = id,
                                    name = videoName,
                                    path = videoPath,
                                    uri = Uri.fromFile(file),
                                    thumbImage = videoThumbnail
                                )

                                viewModels.add(VideoItemViewModel(videoModel))

                            } catch (e: Throwable) {
                                ExceptionHelper.printStackTrace(e)
                            }

                        } while (cursor.moveToNext())

                        callback.invoke(viewModels)
                    }
                }
            }

        }
    }
}