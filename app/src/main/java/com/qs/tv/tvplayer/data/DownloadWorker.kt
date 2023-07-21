package com.qs.tv.tvplayer.data

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.qs.tv.tvplayer.utils.DownloadData
import com.qs.tv.tvplayer.utils.FileUtils
import com.qs.tv.tvplayer.utils.JsonKeys
import java.io.File
import java.io.IOException

class DownloadWorker(private val mContext: Context, workerParams: WorkerParameters) : Worker(mContext, workerParams) {

    override fun doWork(): Result {

        val data = inputData
        val url: String? = data.getString(JsonKeys.KEY_URL)
        val path: String? = data.getString(JsonKeys.KEY_PATH)

        if (url != null && path != null) {
            val file = File(path)
            if (FileUtils.isExit(path)) {
                FileUtils.deleteFile(file)
            }

            try {
                DownloadData.download(url, file)
            } catch (e: IOException) {
                return Result.failure()
            }
        }

        return Result.success()
    }

    private fun startDownload(url: String, file: File) {
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        request.setDescription("Download video...") // display in android notification
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
}