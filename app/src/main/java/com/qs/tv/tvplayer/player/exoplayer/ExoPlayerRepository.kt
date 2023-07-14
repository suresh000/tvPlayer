package com.qs.tv.tvplayer.player.exoplayer

import android.app.Activity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.model.VideoModel
import com.qs.tv.tvplayer.utils.VideoUtil
import com.qs.tv.tvplayer.utils.helpers.exoplayer.ExoPlayerHelper

@UnstableApi
class ExoPlayerRepository(private val mActivity: Activity,
                          private val mVideoId: String = "",
                          private val mExoPlayerHelper: ExoPlayerHelper
) {

    @JvmField
    val timerVisibility = ObservableBoolean(false)
    @JvmField
    val timerValue = ObservableField("30")

    private val mVideoList = ArrayList<VideoModel>()
    private var currentPosition = 0

    init {

        mExoPlayerHelper.mUpdateTimerCallback = {
            timerVisibility.set(true)
            timerValue.set((it / 1000).toString())
        }
        mExoPlayerHelper.mPlayNextCallback = {
            currentPosition += 1
            if (currentPosition < mVideoList.size) {
                playVideo(mVideoList[currentPosition])
            }
        }

        VideoUtil.getVideoList(mActivity) {
            mVideoList.addAll(it)

            loop@for ((index, video) in mVideoList.withIndex()) {
                if (video.id == mVideoId) {
                    currentPosition = index
                    break@loop
                }
            }

            playVideo(mVideoList[currentPosition])
        }
    }

    private fun playVideo(video: VideoModel) {
        mActivity.runOnUiThread {
            timerVisibility.set(false)
            mExoPlayerHelper.play(video.uri)
        }
    }
}