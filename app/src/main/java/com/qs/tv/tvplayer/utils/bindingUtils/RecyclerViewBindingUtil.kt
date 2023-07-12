package com.qs.tv.tvplayer.utils.bindingUtils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object RecyclerViewBindingUtil {

    private const val SET_ADAPTER = "setAdapter"
    private const val SET_ADAPTER_HORIZONTAL = "setAdapterHorizontal"
    private const val SET_ADAPTER_VERTICAL = "setAdapterVertical"
    private const val SET_ADAPTER_GRID_VERTICAL = "setAdapterGridVertical"
    private const val SET_ADAPTER_STAGGERED_GRID_VERTICAL = "setAdapterStaggeredGridVertical"
    private const val SET_ADAPTER_GRID_COUNT = "setAdapterGridCount"
    private const val SET_ADAPTER_VERTICAL_NESTED_SCROLLING_FALSE = "setAdapterVerticalNestedScrollingFalse"

    @JvmStatic
    @BindingAdapter(SET_ADAPTER)
    fun <T : RecyclerView.ViewHolder?> setAdapter(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<T>?
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter(SET_ADAPTER_HORIZONTAL)
    fun <T : RecyclerView.ViewHolder?> setAdapterHorizontal(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<T>?
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.HORIZONTAL, false
            )
            recyclerView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter(SET_ADAPTER_VERTICAL)
    fun <T : RecyclerView.ViewHolder?> setAdapterVertical(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<T>?
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.VERTICAL, false
            )
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter(SET_ADAPTER_VERTICAL_NESTED_SCROLLING_FALSE)
    fun <T : RecyclerView.ViewHolder?> setAdapterVerticalNestedScrollingFalse(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<T>?
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.VERTICAL, false
            )
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter(SET_ADAPTER_GRID_VERTICAL, SET_ADAPTER_GRID_COUNT)
    fun <T : RecyclerView.ViewHolder?> setAdapterGrid(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<T>?,
        count: Int
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.layoutManager = GridLayoutManager(
                recyclerView.context, count,
                GridLayoutManager.VERTICAL, false
            )
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter = adapter
        }
    }

    @BindingAdapter(SET_ADAPTER_STAGGERED_GRID_VERTICAL, SET_ADAPTER_GRID_COUNT)
    fun <T : RecyclerView.ViewHolder?> setAdapterStaggeredGrid(
        recyclerView: RecyclerView?,
        adapter: RecyclerView.Adapter<T>?,
        count: Int
    ) {
        if (recyclerView != null && adapter != null) {
            recyclerView.layoutManager = StaggeredGridLayoutManager(
                count,
                StaggeredGridLayoutManager.VERTICAL
            )
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.adapter = adapter
        }
    }
}