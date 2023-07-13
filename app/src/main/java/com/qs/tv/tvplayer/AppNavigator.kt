package com.qs.tv.tvplayer

import android.content.Context
import android.content.Intent
import com.qs.tv.tvplayer.player.PlayerActivity
import com.qs.tv.tvplayer.utils.JsonKeys

class AppNavigator private constructor() {

    companion object {

        @JvmStatic
        fun navigateToPlayerActivity(context: Context, videoId: String) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(JsonKeys.KEY_VIDEO_ID, videoId)
            context.startActivity(intent)
        }

    }

}