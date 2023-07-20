package com.qs.tv.tvplayer.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.BaseFragment
import com.qs.tv.tvplayer.base.adapter.ViewModelItem
import com.qs.tv.tvplayer.data.DownloadDummyImage
import com.qs.tv.tvplayer.data.DownloadDummyVideo
import com.qs.tv.tvplayer.databinding.FragmentHomeBinding
import com.qs.tv.tvplayer.room.AppRoomDatabase
import com.qs.tv.tvplayer.room.entity.PlayerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@UnstableApi
class HomeFragment : BaseFragment() {

    private var mBinding: FragmentHomeBinding? = null
    private lateinit var mVm: HomeViewModel
    private lateinit var listLiveData: LiveData<List<PlayerItem>>
    private val mAdapter = VideoAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
            listLiveData = AppRoomDatabase.getInstance(requireContext()).playerItemDao().getListLiveData()

            with(mBinding!!) {
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = mAdapter
            }

            val repository = HomeRepository(requireActivity())
            val factory = HomeViewModelFactory(repository)
            mVm = ViewModelProvider(this, factory)[HomeViewModel::class.java]
            mBinding!!.vm = mVm
            mBinding!!.repository = mVm.mRepository

            getVideos()
        }

        return mBinding!!.root
    }

    private fun getVideos() {
        mVm.mRepository.isProgress.set(true)

        /*VideoImageObject.getViewModelItem(requireContext()) { list ->
            requireActivity().runOnUiThread {
                mVm.mRepository.isProgress.set(false)
                if (list.isNotEmpty()) {
                    mAdapter.refreshList(list)
                } else {
                    AppUtils.showToast(requireContext(), "No data found", Toast.LENGTH_SHORT)
                }
            }
        }*/

        Thread(FetchData()).start()
    }

    private inner class FetchData : Runnable {

        override fun run() {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    val count = AppRoomDatabase.getInstance(requireContext()).playerItemDao().getCount()
                    if (count == 0) {
                        val videoWorkRequest: WorkRequest =
                            OneTimeWorkRequestBuilder<DownloadDummyVideo>()
                                .build()
                        WorkManager
                            .getInstance(requireContext())
                            .enqueue(videoWorkRequest)

                        val imageWorkRequest: WorkRequest =
                            OneTimeWorkRequestBuilder<DownloadDummyImage>()
                                .build()
                        WorkManager
                            .getInstance(requireContext())
                            .enqueue(imageWorkRequest)
                    }

                }

                CoroutineScope(Dispatchers.Main).launch {
                    requireActivity().runOnUiThread {
                        listLiveData.removeObservers(this@HomeFragment)
                        listLiveData = listLiveData.switchMap {
                            AppRoomDatabase.getInstance(requireContext()).playerItemDao().getListLiveData()
                        }
                        listLiveData.observe(this@HomeFragment) { list ->
                            Thread(LoadData(list)).start()
                        }

                    }
                }

            }
        }

        private inner class LoadData(private val mList: List<PlayerItem>) : Runnable {
            override fun run() {
                val viewModels = ArrayList<ViewModelItem>()

                for (item in mList) {
                    viewModels.add(PlayerItemViewModel(requireContext(), item))
                }

                requireActivity().runOnUiThread {
                    mVm.mRepository.isProgress.set(false)
                    mAdapter.refreshList(viewModels)
                }
            }

        }

    }

    override fun getCurrentFragment(): Fragment? {
        return null
    }

}