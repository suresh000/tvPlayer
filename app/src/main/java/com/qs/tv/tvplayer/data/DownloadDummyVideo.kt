package com.qs.tv.tvplayer.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadDummyVideo(private val mContext: Context, workerParams: WorkerParameters) :
    Worker(mContext, workerParams) {

    override fun doWork(): Result {



        return Result.success()
    }
}