package com.qs.tv.tvplayer.objects

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.qs.tv.tvplayer.utils.FileUtils


object DeviceMemoryObject {

    private fun getTotalBytes(path: String): Long {
        val statFs = StatFs(path)
        return statFs.totalBytes
    }

    private fun getAvailableBytes(path: String): Long {
        val statFs = StatFs(path)
        return statFs.availableBytes
    }

    private fun getUsedBytes(path: String): Long {
        val statFs = StatFs(path)
        return statFs.totalBytes - statFs.availableBytes
    }

    fun isExternalStorageAvailable(context: Context) : Boolean {
        var root = Environment.getExternalStorageDirectory()
        return if (!FileUtils.isExternalStorageRemovable(root)) {
            val availableBytes = getAvailableBytes(root.absolutePath)
            val gb = bytesToGb(availableBytes)
            gb > 1
        } else {
            root = context.getExternalFilesDir(null)
            val availableBytes = getAvailableBytes(root.absolutePath)
            val gb = bytesToGb(availableBytes)
            gb > 1
        }
    }

    private fun bytesToGb(bytes: Long) : Int {
        val kb: Double = (bytes / 1024).toDouble()
        val mb: Double = kb / 1024
        val gb: Double = mb / 1024
        return gb.toInt()
    }

}