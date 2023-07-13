package com.app.guniguru.utils.helpers.exoplayer

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.text.TextUtils
import androidx.databinding.ObservableBoolean
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.State
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@UnstableApi
class ExoPlayerHelper(base: Context, playerView: PlayerView,
                                   private val mCallback: ExoPlayerCallback) : ContextWrapper(base) {

    @JvmField
    val isVideoPlay = ObservableBoolean(false)

    private val player: ExoPlayer
    //private val mediaSession: MediaSession = MediaSession.Builder(baseContext, player).build()

    init {
        val builder = ExoPlayer.Builder(baseContext)
        builder.setSeekBackIncrementMs(10000)
        builder.setSeekForwardIncrementMs(10000)
        player = builder.build()
    }

    private val listener = object : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {

        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {

        }

        override fun onPlaybackStateChanged(@State state: Int) {
            when (state) {
                Player.STATE_READY -> {  }
                Player.STATE_ENDED -> {  }
                Player.STATE_BUFFERING -> {  }
                Player.STATE_IDLE -> {  }
                else -> {  }
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            val cause = error.cause
        }

    }

    init {
        player.addListener(listener)
        playerView.player = player
    }

    fun play(videoUrl: String) {
        if (!TextUtils.isEmpty(videoUrl)) {

            val mediaItemBuilder = MediaItem.Builder().setMediaId("mediaId")
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .setTag("myAppData").setUri(Uri.parse(videoUrl))
            val mediaItem = mediaItemBuilder.build()

            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
            player.play()
        }
    }

    fun play(uri: Uri) {
        val mediaItemBuilder = MediaItem.Builder().setMediaId("mediaId")
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .setTag("myAppData").setUri(uri)
        val mediaItem = mediaItemBuilder.build()

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player.play()
    }

    fun stop() {
        player.playWhenReady = false
        player.stop()
    }

    fun resume() {
        player.playWhenReady = true
        player.prepare()
        player.play()
    }

    fun release() {
        player.release()
    }

}