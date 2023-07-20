package com.qs.tv.tvplayer.player.exoplayer

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.databinding.ActivityExoPlayerBinding
import com.qs.tv.tvplayer.utils.JsonKeys
import com.qs.tv.tvplayer.utils.helpers.exoplayer.ExoPlayerHelper

@UnstableApi
class ExoPlayerActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityExoPlayerBinding
    private lateinit var mVm: ExoPlayerViewModel
    private lateinit var mExoPlayerHelper: ExoPlayerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_exo_player)

        val videoId = intent.getIntExtra(JsonKeys.KEY_ID, -1)

        mExoPlayerHelper = ExoPlayerHelper(this, mBinding.playerView)

        val repository = ExoPlayerRepository(this, videoId, mExoPlayerHelper, mBinding.viewFlipper)
        val factory = ExoPlayerViewModelFactory(repository)
        mVm = ViewModelProvider(this, factory)[ExoPlayerViewModel::class.java]
        mBinding.vm = mVm
        mBinding.repository = mVm.mRepository

    }

    override fun onDestroy() {
        mExoPlayerHelper.destroy()
        super.onDestroy()
    }
}