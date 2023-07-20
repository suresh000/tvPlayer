package com.qs.tv.tvplayer.player.exoplayer

import android.app.Activity
import android.net.Uri
import android.widget.ViewFlipper
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.room.AppRoomDatabase
import com.qs.tv.tvplayer.room.entity.PlayerItem
import com.qs.tv.tvplayer.utils.CountDownTimer
import com.qs.tv.tvplayer.utils.helpers.exoplayer.ExoPlayerHelper
import java.io.File

@UnstableApi
class ExoPlayerRepository(private val mActivity: Activity,
                          private val mVideoId: Int,
                          private val mExoPlayerHelper: ExoPlayerHelper,
                          private val mViewFlipper: ViewFlipper
) {

    @JvmField
    val mModel = ObservableField<PlayerItem>()
    @JvmField
    val imageUri = ObservableField<Uri>()
    @JvmField
    val timerVisibility = ObservableBoolean(false)
    @JvmField
    val timerValue = ObservableField("30")

    private val mVideoList = ArrayList<PlayerItem>()
    private var currentPosition = 0
    private lateinit var mImageCountDownTimer: CountDownTimer

    init {

        mExoPlayerHelper.mUpdateTimerCallback = {
            timerVisibility.set(true)
            timerValue.set((it / 1000).toString())
        }
        mExoPlayerHelper.mPlayNextCallback = {
            currentPosition += 1
            if (currentPosition >= mVideoList.size) {
                currentPosition = 0
            }
            mActivity.runOnUiThread {
                val model = mVideoList[currentPosition]
                mModel.set(model)
                if (model.isVideo) {
                    mViewFlipper.displayedChild = 0
                    playVideo(model)
                } else {
                    mViewFlipper.displayedChild = 1
                    imageUri.set(Uri.fromFile(File(model.storagePath)))
                    startImageTimer()
                }
            }
        }

        Thread(Init()).start()
    }

    private inner class Init : Runnable {
        override fun run() {
            val list = AppRoomDatabase.getInstance(mActivity).playerItemDao().getList()
            if (list.isNotEmpty()) {
                mVideoList.addAll(list)

                loop@for ((index, video) in mVideoList.withIndex()) {
                    if (video.id == mVideoId) {
                        currentPosition = index
                        break@loop
                    }
                }

                mActivity.runOnUiThread {
                    val model = mVideoList[currentPosition]
                    mModel.set(model)
                    if (model.isVideo) {
                        mViewFlipper.displayedChild = 0
                        playVideo(model)
                    } else {
                        mViewFlipper.displayedChild = 1
                        imageUri.set(Uri.fromFile(File(model.storagePath)))
                        startImageTimer()
                    }
                }
            }
        }

    }

    private fun playVideo(video: PlayerItem) {
        mActivity.runOnUiThread {
            timerVisibility.set(false)
            mExoPlayerHelper.play(Uri.fromFile(File(video.storagePath)))
        }
    }

    private fun startImageTimer() {
        mImageCountDownTimer = object : CountDownTimer(5000L, 5000L) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                mExoPlayerHelper.mPlayNextCallback?.invoke()
                cancel()
            }

        }
        mImageCountDownTimer.start()
    }
}