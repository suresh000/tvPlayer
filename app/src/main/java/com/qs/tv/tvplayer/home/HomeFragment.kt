package com.qs.tv.tvplayer.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.GridLayoutManager
import com.qs.sharedcode.utils.AppUtils
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseFragment
import com.qs.tv.tvplayer.databinding.FragmentHomeBinding
import com.qs.tv.tvplayer.objects.VideoImageObject
import com.qs.tv.tvplayer.utils.PermissionUtil

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

            if (PermissionUtil.isPermissionGranted(requireContext())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_EXTERNAL_STORAGE))
                } else {
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                }
            } else {
                getVideos()
            }
        }

        return mBinding!!.root
    }

    private fun getVideos() {
        mVm.mRepository.isProgress.set(true)

        VideoImageObject.getViewModelItem(requireContext()) { list ->
            requireActivity().runOnUiThread {
                mVm.mRepository.isProgress.set(false)
                if (list.isNotEmpty()) {
                    mAdapter.refreshList(list)
                } else {
                    AppUtils.showToast(requireContext(), "No data found", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                permissions: Map<String, Boolean> ->

            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    getVideos()
                }
            }
        }

    override fun getCurrentFragment(): Fragment? {
        return null
    }

}