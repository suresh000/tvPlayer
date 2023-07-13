package com.qs.tv.tvplayer.player

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.app.guniguru.utils.helpers.exoplayer.ExoPlayerCallback
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseFragmentActivity
import com.qs.tv.tvplayer.databinding.ActivityPlayerBinding
import com.qs.tv.tvplayer.home.VideoItemViewModel
import com.qs.tv.tvplayer.utils.JsonKeys
import com.qs.tv.tvplayer.utils.VideoUtil

class PlayerActivity : BaseFragmentActivity(), ExoPlayerCallback {

    private lateinit var mBinding: ActivityPlayerBinding
    private lateinit var mVm: PlayerViewModel
    private var videoId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player)

        videoId = intent.getStringExtra(JsonKeys.KEY_VIDEO_ID).toString()

        val repository = PlayerRepository()
        val factory = PlayerViewModelFactory(repository)
        mVm = ViewModelProvider(this, factory)[PlayerViewModel::class.java]
        mBinding.vm = mVm
        mBinding.repository = mVm.mRepository

        setUpIntro()

    }

    private fun setUpIntro() {

        VideoUtil.getVideoList(this) { viewModels ->
            runOnUiThread {
                val list = ArrayList<Fragment>()
                var currentPosition = 0
                for ((index, item) in viewModels.withIndex()) {
                    if (item is VideoItemViewModel) {
                        val video = item.mVideo.get()
                        if (video != null) {
                            if (video.id == videoId) {
                                currentPosition = index
                            }
                            list.add(PlayerFragment.newInstance(item.mVideo.get()!!))
                        }
                    }
                }

                val adapter = PlayerPagerAdapter(this, list)
                mBinding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                mBinding.viewPager.adapter = adapter

                mBinding.viewPager.currentItem = currentPosition
            }
        }

        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {

            }

        })
    }

    override fun getCurrentFragment(): Fragment? {
        return null
    }

    override fun exoFullscreen() {

    }

}