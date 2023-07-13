package com.qs.tv.tvplayer.player

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PlayerPagerAdapter(fragmentActivity: FragmentActivity, items: MutableList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    private val itemList: MutableList<Fragment>

    init {
        itemList = items
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun createFragment(position: Int): Fragment {
        return itemList[position]
    }
}