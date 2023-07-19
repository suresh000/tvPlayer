package com.qs.tv.tvplayer.data

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.qs.tv.tvplayer.room.AppRoomDatabase
import com.qs.tv.tvplayer.room.entity.PlayerItem
import com.qs.tv.tvplayer.utils.DownloadData
import com.qs.tv.tvplayer.utils.FileUtils
import java.io.File


class DownloadDummyImage(private val mContext: Context, workerParams: WorkerParameters) :
    Worker(mContext, workerParams) {

    override fun doWork(): Result {

        mContext.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        for ((index, url) in DummyUrls.images.withIndex()) {

            val name = "Image$index"
            val path = FileUtils.getImagePath() + name + ".jpg"
            val file = File(path)
            if (FileUtils.isExit(path)) {
                FileUtils.deleteFile(file)
            }

            val item = PlayerItem()
            item.name = name
            item.storagePath = path
            item.url = url
            item.storageThumbnailPath = path
            item.thumbnailUrl = ""
            item.isVideo = false
            AppRoomDatabase.getInstance(mContext).playerItemDao().insert(item)

            //startDownload(url, file)
            DownloadData.download(url, file)

        }

        return Result.success()
    }

    private fun startDownload(url: String, file: File) {
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        request.setDescription("Download image...") // display in android notification
        request.setTitle("tvPlayer") // display in android notification
        //request.setDestinationInExternalFilesDir(mContext, FileUtils.getImagePath(mContext), path)
        //val destinationUri = FileProvider.getUriForFile(mContext, FileUtils.FILE_PROVIDER, File(path))
        request.setDestinationUri(Uri.fromFile(file))
        val manager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        /*
        * start the download and return the id of the download.
        * this id can be used to get info about the file (the size, the download progress ...)
        * you can also stop the download by using this id
        * */
        val downloadId = manager.enqueue(request)
    }

    private val onComplete: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(ctxt: Context?, intent: Intent?) {
            // your code
        }
    }
}