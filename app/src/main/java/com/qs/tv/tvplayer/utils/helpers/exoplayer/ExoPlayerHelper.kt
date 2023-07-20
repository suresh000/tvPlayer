package com.qs.tv.tvplayer.utils.helpers.exoplayer

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Handler
import android.os.Looper
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
import com.qs.tv.tvplayer.utils.CountDownTimer


@UnstableApi
class ExoPlayerHelper(base: Context, playerView: PlayerView) : ContextWrapper(base) {

    @JvmField
    val isVideoPlay = ObservableBoolean(false)

    private var player: ExoPlayer? = null
    //private val mediaSession: MediaSession = MediaSession.Builder(baseContext, player).build()
    private lateinit var mCountDownTimer: CountDownTimer

    var mUpdateTimerCallback: ((Long) -> Unit?)? = null
    var mPlayNextCallback: (() -> Unit?)? = null

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
                Player.STATE_READY -> {
                    if (player != null) {
                        val duration = player!!.duration
                        val durationSec = duration / 1000
                        var position = durationSec
                        if (durationSec > 30) {
                            position = duration - (30 * 1000).toLong()
                        }
                        val handler = Handler(Looper.getMainLooper())
                        player!!.createMessage { messageType: Int, payload: Any? ->

                            mCountDownTimer = object : CountDownTimer(30000L, 1000L) {

                                override fun onTick(millisUntilFinished: Long) {
                                    /*if (mUpdateTimerCallback != null) {
                                        mUpdateTimerCallback!!.invoke((duration - player!!.currentPosition))
                                    }*/
                                }

                                override fun onFinish() {
                                    cancel()
                                }

                            }
                            mCountDownTimer.start()
                        }
                            .setPosition(position)
                            .setHandler(handler)
                            .send()
                    }
                }
                Player.STATE_ENDED -> {
                    if (mPlayNextCallback != null) {
                        mPlayNextCallback!!.invoke()
                    }
                }
                Player.STATE_BUFFERING -> {  }
                Player.STATE_IDLE -> {  }
                else -> {  }
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            val cause = error.cause
        }

        override fun onEvents(player: Player, events: Player.Events) {

        }

    }

    init {
        if (player != null) {
            player!!.addListener(listener)
            playerView.player = player
        }
    }

    fun play(videoUrl: String) {
        if (!TextUtils.isEmpty(videoUrl)) {
            if (player != null) {
                val mediaItemBuilder = MediaItem.Builder().setMediaId("mediaId")
                    .setMimeType(MimeTypes.APPLICATION_MP4)
                    .setTag("myAppData").setUri(Uri.parse(videoUrl))
                val mediaItem = mediaItemBuilder.build()

                player!!.setMediaItem(mediaItem)
                player!!.prepare()
                player!!.playWhenReady = true
                player!!.play()
            }
        }
    }

    fun play(uri: Uri) {
        if (player != null) {
            val mediaItemBuilder = MediaItem.Builder().setMediaId("mediaId")
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .setTag("myAppData").setUri(uri)
            val mediaItem = mediaItemBuilder.build()

            player!!.setMediaItem(mediaItem)
            player!!.prepare()
            player!!.playWhenReady = true
            player!!.play()
        }
    }

    fun stop() {
        if (player != null) {
            player!!.playWhenReady = false
            player!!.stop()
        }
    }

    fun resume() {
        if (player != null) {
            player!!.playWhenReady = true
            player!!.prepare()
            player!!.play()
        }
    }

    fun release() {
        if (player != null) {
            player!!.release()
        }
    }

    fun destroy() {
        if (player != null) {
            stop()
            player = null
        }
    }

}