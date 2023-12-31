package com.qs.tv.tvplayer.utils

import android.content.ContentResolver
import android.content.Context
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.home.VideoItemViewModel
import com.qs.tv.tvplayer.model.VideoModel
import com.qs.tv.tvplayer.utils.helpers.ExceptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


@UnstableApi
class VideoUtil private constructor() {

    companion object {

        @JvmStatic
        fun getVideoItemList(context: Context, callback: (List<ViewModelItem>) -> Unit) {
            val viewModels = ArrayList<ViewModelItem>()

            val contentResolver: ContentResolver = context.contentResolver
            val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DURATION)
            CoroutineScope(Dispatchers.IO).launch {
                contentResolver.query(uri, projection, null, null,
                    MediaStore.Video.Media.DATE_ADDED + " DESC")?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        do {
                            val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                            val videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                            val videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                            val imageBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)

                            try {
                                val file = File(videoPath)
                                val type = AppUtil.getMimeType(context, Uri.fromFile(file))
                                val videoModel = VideoModel(
                                    id = id,
                                    name = videoName,
                                    path = videoPath,
                                    type = type!!,
                                    uri = Uri.fromFile(file),
                                    imageBitmap = imageBitmap,
                                    isVideo = true
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

        fun getImageItemList(context: Context, callback: (List<ViewModelItem>) -> Unit) {
            val viewModels = ArrayList<ViewModelItem>()

            val contentResolver: ContentResolver = context.contentResolver
            val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(MediaStore.Images.Media.TITLE, MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID, MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED)

            CoroutineScope(Dispatchers.IO).launch {
                contentResolver.query(uri, projection, null, null,
                    MediaStore.Video.Media.DATE_ADDED + " DESC")?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        do {
                            val id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                            val videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE))
                            val videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))

                            try {
                                val file = File(videoPath)
                                val type = AppUtil.getMimeType(context, Uri.fromFile(file))
                                val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))

                                val videoModel = VideoModel(
                                    id = id,
                                    name = videoName,
                                    path = videoPath,
                                    type = type!!,
                                    uri = Uri.fromFile(file),
                                    imageBitmap = imageBitmap,
                                    isVideo = false
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

        @JvmStatic
        fun getVideoList(context: Context, callback: (List<VideoModel>) -> Unit) {

            val videoList = ArrayList<VideoModel>()

            val contentResolver: ContentResolver = context.contentResolver
            val videoUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI


            CoroutineScope(Dispatchers.IO).launch {

                val videoProjection = arrayOf(MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Video.Media.MIME_TYPE,
                    MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DURATION)
                contentResolver.query(videoUri, videoProjection, null, null,
                    MediaStore.Video.Media.DATE_ADDED + " DESC")?.use { videoCursor ->
                    if (videoCursor.moveToFirst()) {
                        do {
                            val id = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media._ID))
                            val videoName = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                            val videoPath = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media.DATA))
                            val videoType = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE))
                            val videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)

                            try {
                                val file = File(videoPath)
                                val type = AppUtil.getMimeType(context, Uri.fromFile(file))
                                val videoModel = VideoModel(
                                    id = id,
                                    name = videoName,
                                    path = videoPath,
                                    type = type!!,
                                    uri = Uri.fromFile(file),
                                    imageBitmap = videoThumbnail
                                )

                                videoList.add(videoModel)

                            } catch (e: Throwable) {
                                ExceptionHelper.printStackTrace(e)
                            }

                        } while (videoCursor.moveToNext())

                        callback.invoke(videoList)
                    }
                }
            }

        }

        @JvmStatic
        fun getImageList(context: Context, callback: (List<VideoModel>) -> Unit) {

            val imageList = ArrayList<VideoModel>()

            val contentResolver: ContentResolver = context.contentResolver
            val imageUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val imageProjection = arrayOf(MediaStore.Images.Media.TITLE, MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID, MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED)

            contentResolver.query(imageUri, imageProjection, null, null,
                MediaStore.Video.Media.DATE_ADDED + " DESC")?.use { imageCursor ->

                if (imageCursor.moveToFirst()) {
                    do {
                        val id = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media._ID))
                        val videoName = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.TITLE))
                        val videoPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA))
                        val videoType = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE))
                        val videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)

                        try {
                            val file = File(videoPath)
                            val type = AppUtil.getMimeType(context, Uri.fromFile(file))
                            val videoModel = VideoModel(
                                id = id,
                                name = videoName,
                                path = videoPath,
                                type = type!!,
                                uri = Uri.fromFile(file),
                                imageBitmap = videoThumbnail
                            )

                            imageList.add(videoModel)

                        } catch (e: Throwable) {
                            ExceptionHelper.printStackTrace(e)
                        }

                    } while (imageCursor.moveToNext())

                    callback.invoke(imageList)
                }
            }
        }
    }
}