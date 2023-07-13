package com.qs.tv.tvplayer.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.media3.common.util.UnstableApi
import com.app.guniguru.utils.helpers.exoplayer.ExoPlayerCallback
import com.app.guniguru.utils.helpers.exoplayer.ExoPlayerHelper
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.databinding.FragmentPlayerBinding
import com.qs.tv.tvplayer.model.VideoModel
import com.qs.tv.tvplayer.utils.JsonKeys

@UnstableApi
class PlayerFragment : Fragment(), ExoPlayerCallback {

    private lateinit var mBinding: FragmentPlayerBinding
    private val mVm: PlayerViewModel by activityViewModels()

    private lateinit var mVideoModel: VideoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mVideoModel = it.getSerializable(JsonKeys.KEY_VIDEO_MODEL) as VideoModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)

        mBinding.vm = mVm
        mBinding.repository = mVm.mRepository

        val exoPlayerHelper = ExoPlayerHelper(requireContext(), mBinding.playerView, this)
        exoPlayerHelper.play(mVideoModel.uri)

        return mBinding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(video: VideoModel) : PlayerFragment {
            val fragment = PlayerFragment()
            val bundle = Bundle()
            bundle.putSerializable(JsonKeys.KEY_VIDEO_MODEL, video)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun exoFullscreen() {

    }

}