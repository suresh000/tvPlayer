package com.qs.tv.tvplayer.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.GridLayoutManager
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseFragment
import com.qs.tv.tvplayer.databinding.FragmentHomeBinding
import com.qs.tv.tvplayer.objects.VideoImageObject

@UnstableApi
class HomeFragment : BaseFragment() {

    private var mBinding: FragmentHomeBinding? = null
    private lateinit var mVm: HomeViewModel
    private val mAdapter = VideoAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

            with(mBinding!!) {
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = mAdapter
            }

            val repository = HomeRepository(requireActivity())
            val factory = HomeViewModelFactory(repository)
            mVm = ViewModelProvider(this, factory)[HomeViewModel::class.java]
            mBinding!!.vm = mVm
            mBinding!!.repository = mVm.mRepository

            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_DENIED) {

                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }

            } else {
                getVideos()
            }
        }

        return mBinding!!.root
    }

    private fun getVideos() {
        mVm.mRepository.isProgress.set(true)

        VideoImageObject.getViewModelItem(requireContext()) {
            requireActivity().runOnUiThread {
                mAdapter.refreshList(it)
                mVm.mRepository.isProgress.set(false)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getVideos()
            }
        }

    override fun getCurrentFragment(): Fragment? {
        return null
    }

}