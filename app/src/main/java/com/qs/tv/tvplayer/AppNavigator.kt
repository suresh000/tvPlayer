package com.qs.tv.tvplayer

import android.content.Context
import android.content.Intent
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.player.PlayerActivity
import com.qs.tv.tvplayer.player.exoplayer.ExoPlayerActivity
import com.qs.tv.tvplayer.utils.JsonKeys

@UnstableApi
class AppNavigator private constructor() {

    companion object {

        @JvmStatic
        fun navigateToPlayerActivity(context: Context, videoId: String) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(JsonKeys.KEY_VIDEO_ID, videoId)
            context.startActivity(intent)
        }

        @JvmStatic
        fun navigateToExoPlayerActivity(context: Context, videoId: String) {
            val intent = Intent(context, ExoPlayerActivity::class.java)
            intent.putExtra(JsonKeys.KEY_VIDEO_ID, videoId)
            context.startActivity(intent)
        }

    }

}