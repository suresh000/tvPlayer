package com.qs.tv.tvplayer.utils.bindingUtils

import android.view.View
import androidx.databinding.BindingAdapter

object MarginBindingUtil {

    private const val MARGIN_START = "marginStart"
    private const val MARGIN_TOP = "marginTop"
    private const val MARGIN_END = "marginEnd"
    private const val MARGIN_BOTTOM = "marginBottom"

    @JvmStatic
    @BindingAdapter(MARGIN_START, MARGIN_TOP, MARGIN_END, MARGIN_BOTTOM)
    fun setMargin(view: View?, start: Float, top: Float, end: Float, bottom: Float) {
    }
}