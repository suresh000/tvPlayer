package com.qs.tv.tvplayer.home

import android.annotation.SuppressLint
import com.qs.tv.tvplayer.R
import com.qs.tv.tvplayer.base.adapter.DataBindingRecyclerViewAdapter
import com.qs.tv.tvplayer.base.adapter.ViewModelItem

class VideoAdapter(viewModels: MutableList<ViewModelItem>) : DataBindingRecyclerViewAdapter(viewModels) {

    private val mViewModelMap = HashMap<Class<*>, Int>()

    init {
        mViewModelMap[VideoItemViewModel::class.java] = R.layout.video_item
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(itemList: MutableList<ViewModelItem>) {
        mViewModels = itemList
        notifyDataSetChanged()
    }

    override val viewModelLayoutMap: Map<Class<*>, Int>
        get() = mViewModelMap

}