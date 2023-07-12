package com.qs.tv.tvplayer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtil private constructor() {

    init {
        throw IllegalArgumentException(NetworkUtil::class.java.name)
    }

    companion object {
        @JvmStatic
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
            return netInfo != null && netInfo.isConnected
        }

        @JvmStatic
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfos = cm.allNetworkInfo
            if (networkInfos.isNotEmpty()) {
                for (info in networkInfos) {
                    if (info != null && info.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
            return false
        }
    }
}