package com.qs.tv.tvplayer.utils

import com.qs.tv.tvplayer.utils.helpers.ExceptionHelper
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL


class DownloadData {

    companion object {

        @JvmStatic
        fun download(downloadUrl: String, file: File) {
            var count = 0
            try {
                val url = URL(downloadUrl)
                val connection = url.openConnection()
                connection.connect()

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                val lengthOfFile = connection.contentLength

                // download the file
                val input: InputStream = BufferedInputStream(url.openStream(), lengthOfFile)

                // Output stream
                val output: OutputStream = FileOutputStream(file)

                val data = ByteArray(1024)

                while (input.read(data).also { count = it } != -1) {
                    output.write(data, 0, count)
                }

                output.flush()

                output.close()
                input.close()

            } catch (e: Exception) {
                ExceptionHelper.printStackTrace(e)
            }

        }
    }

}