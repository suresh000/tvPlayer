package com.qs.tv.tvplayer.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.FileProvider
import com.qs.sharedcode.utils.AppUtils
import java.io.File
import java.io.IOException
import java.util.Locale

class FileUtils private constructor() {

    init {
        throw IllegalStateException(javaClass.name)
    }

    // Checks if external storage is available for read and write

    companion object {
        const val FILE_PROVIDER = "com.qs.tv.tvplayer.fileprovider"

        private val extensions = arrayOf(
            "avi", "3gp", "mp4", "mp3", "jpeg", "jpg",
            "gif", "png",
            "pdf", "docx", "doc", "xls", "xlsx", "csv", "ppt", "pptx",
            "txt", "zip", "rar"
        )

        @JvmStatic
        @Throws(ActivityNotFoundException::class, IOException::class)
        fun openFile(activity: Activity, url: File) {
            // Create URI
            //Uri uri = Uri.fromFile(url);

            //TODO you want to use this method then create file provider in androidmanifest.xml with fileprovider name
            val uri = FileProvider.getUriForFile(activity, FILE_PROVIDER, url)
            val urlString = url.toString().lowercase(Locale.getDefault())
            val intent = Intent(Intent.ACTION_VIEW)

            val resInfoList = activity.packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                activity.grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

            if (urlString.lowercase(Locale.getDefault()).lowercase(Locale.getDefault())
                    .contains(".doc")
                || urlString.lowercase(Locale.getDefault()).contains(".docx")
            ) {
                // Word document
                intent.setDataAndType(uri, "application/msword")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            } else if (urlString.lowercase(Locale.getDefault()).contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            } else if (urlString.lowercase(Locale.getDefault()).contains(".ppt")
                || urlString.lowercase(Locale.getDefault()).contains(".pptx")
            ) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".xls")
                || urlString.lowercase(Locale.getDefault()).contains(".xlsx")
            ) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".zip")
                || urlString.lowercase(Locale.getDefault()).contains(".rar")
            ) {
                // ZIP file
                intent.setDataAndType(uri, "application/trap")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".wav")
                || urlString.lowercase(Locale.getDefault()).contains(".mp3")
            ) {
                // WAV/MP3 audio file
                intent.setDataAndType(uri, "audio/*")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".jpg")
                || urlString.lowercase(Locale.getDefault()).contains(".jpeg")
                || urlString.lowercase(Locale.getDefault()).contains(".png")
            ) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain")
            } else if (urlString.lowercase(Locale.getDefault()).contains(".3gp")
                || urlString.lowercase(Locale.getDefault()).contains(".mpg")
                || urlString.lowercase(Locale.getDefault()).contains(".mpeg")
                || urlString.lowercase(Locale.getDefault()).contains(".mpe")
                || urlString.lowercase(Locale.getDefault()).contains(".mp4")
                || urlString.lowercase(Locale.getDefault()).contains(".avi")
            ) {
                // Video files
                intent.setDataAndType(uri, "video/*")
            } else {
                intent.setDataAndType(uri, "*/*")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                activity.runOnUiThread {
                    AppUtils.showToast(
                        activity,
                        "Something Wrong. Viewer not found", Toast.LENGTH_LONG
                    )
                }
            }
        }

        @JvmStatic
        fun getAppCacheDir(context: Context): String {
            val dir = File(
                context.cacheDir.toString()
                        + File.separator
                        + "tvPlayer"
                        + File.separator
            )
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir.path + File.separator
        }

        @JvmStatic
        fun getAppPath(context: Context): String {
            val dir = File(
                context.getExternalFilesDir(null).toString()
                        + File.separator
                        + "tvPlayer"
                        + File.separator
            )
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir.path + File.separator
        }

        @JvmStatic
        fun getImagePath(context: Context): String {
            val dir = File(
                context.getExternalFilesDir(null).toString()
                        + File.separator
                        + "tvPlayer"
                        + File.separator
                        + "Image"
                        + File.separator
            )
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir.path + File.separator
        }

        @JvmStatic
        fun getVideoPath(context: Context): String {
            val dir = File(
                context.getExternalFilesDir(null).toString()
                        + File.separator
                        + "tvPlayer"
                        + File.separator
                        + "Video"
                        + File.separator
            )
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir.path + File.separator
        }

        @JvmStatic
        fun isExit(imagePath: String?): Boolean {
            return if (!TextUtils.isEmpty(imagePath)) {
                val uri = Uri.parse(imagePath)
                val file = File(uri.path.toString())
                file.exists()
            } else {
                false
            }
        }

        @JvmStatic
        val isExternalStorageWritable: Boolean
            get() {
                val state = Environment.getExternalStorageState()
                return Environment.MEDIA_MOUNTED == state
            }

        @JvmStatic
        fun isValidExtension(ext: String): Boolean {
            return mutableListOf(*extensions).contains(ext)
        }

        @JvmStatic
        fun getExtension(path: String): String {
            return if (path.contains(".")) path.substring(path.lastIndexOf(".") + 1)
                .lowercase(Locale.getDefault()) else ""
        }

        @JvmStatic
        fun deleteFile(file: File?): Boolean {
            return if (file != null && file.exists()) {
                file.delete()
            } else false
        }

    }
}