package com.qs.tv.tvplayer.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File


class AppUtil private constructor() {

    companion object {

        @JvmStatic
        fun getMimeType(context: Context, uri: Uri): String? {
            val extension: String?

            //Check uri format to avoid null
            extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val mime = MimeTypeMap.getSingleton()
                mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
            } else {
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path.toString())).toString())
            }
            return extension
        }
    }
}