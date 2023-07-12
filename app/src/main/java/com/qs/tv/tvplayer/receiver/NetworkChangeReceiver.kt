package com.qs.tv.tvplayer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.qs.tv.tvplayer.common.internet.InternetLostDialog
import com.qs.tv.tvplayer.utils.NetworkUtil

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (!NetworkUtil.isOnline(context)) {
            InternetLostDialog.show()
        } else {
            InternetLostDialog.hide()
        }

    }
}