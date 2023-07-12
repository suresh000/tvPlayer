package com.qs.tv.tvplayer.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingRecyclerViewHolder<T : ViewDataBinding?>(val binding: T) : RecyclerView.ViewHolder(
    binding!!.root
)