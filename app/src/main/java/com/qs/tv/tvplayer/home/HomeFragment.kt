package com.qs.tv.tvplayer.home

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseFragment
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.databinding.FragmentHomeBinding
import com.qs.tv.tvplayer.model.VideoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
                }

            } else {
                getVideos()
            }
        }

        return mBinding!!.root
    }

    private fun getVideos() {
        mVm.mRepository.isProgress.set(true)
        val viewModels = ArrayList<ViewModelItem>()

        val contentResolver: ContentResolver = requireContext().contentResolver
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        lifecycleScope.launch {
            val job = CoroutineScope(Dispatchers.IO).launch {
                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        do {
                            val videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                            val videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                            val videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)

                            val videoModel = VideoModel(
                                name = videoName,
                                path = videoPath,
                                thumbImage = videoThumbnail
                            )

                            viewModels.add(VideoItemViewModel(videoModel))

                        } while (cursor.moveToNext())
                    }
                }
            }
            job.join()

            requireActivity().runOnUiThread {
                mAdapter.refreshList(viewModels)
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