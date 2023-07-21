package com.qs.tv.tvplayer.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.qs.tv.tvplayer.AppController
import com.qs.tv.tvplayer.objects.DeviceMemoryObject
import com.qs.tv.tvplayer.room.AppRoomDatabase
import com.qs.tv.tvplayer.room.entity.PlayerItem
import com.qs.tv.tvplayer.utils.FileUtils
import com.qs.tv.tvplayer.utils.JsonKeys

class DownloadDummyVideo(private val mContext: Context, workerParams: WorkerParameters) :
    Worker(mContext, workerParams) {

    override fun doWork(): Result {

        //mContext.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        for ((index, url) in DummyUrls.videos.withIndex()) {

            val name = "Video$index"
            val path = if (DeviceMemoryObject.isExternalStorageAvailable(mContext)) {
                FileUtils.getVideoExternalStorageDirectory(mContext) + name + ".mp4"
            } else {
                ""
            }

            val item = PlayerItem()
            item.name = name
            item.storagePath = path
            item.url = url
            item.storageThumbnailPath = path
            item.thumbnailUrl = ""
            item.isVideo = true
            AppRoomDatabase.getInstance(mContext).playerItemDao().insert(item)

            if (DeviceMemoryObject.isExternalStorageAvailable(mContext)) {
                val data = Data.Builder()
                data.putString(JsonKeys.KEY_URL, url)
                data.putString(JsonKeys.KEY_PATH, path)

                val imageWorkRequest: WorkRequest =
                    OneTimeWorkRequestBuilder<DownloadWorker>()
                        .setInputData(data.build())
                        .build()
                WorkManager
                    .getInstance(AppController.instance.applicationContext)
                    .enqueue(imageWorkRequest)
            }

        }

        return Result.success()
    }

    private val onComplete: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(ctxt: Context?, intent: Intent?) {

        }
    }
}