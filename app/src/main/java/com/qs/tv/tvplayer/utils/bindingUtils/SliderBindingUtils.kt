package com.qs.tv.tvplayer.utils.bindingUtils

import androidx.databinding.BindingAdapter
import com.google.android.material.slider.Slider

object SliderBindingUtils {

    private const val SET_SLIDER_TOUCH_LISTENER = "setSliderTouchListener"
    private const val SET_SLIDER_CHANGE_LISTENER = "setSliderChangeListener"

    @JvmStatic
    @BindingAdapter(SET_SLIDER_TOUCH_LISTENER)
    fun setSliderTouchListener(
        slider: Slider,
        listener: Slider.OnSliderTouchListener?
    ) {
        if (listener != null) {
            slider.addOnSliderTouchListener(listener)
        }
    }

    @JvmStatic
    @BindingAdapter(SET_SLIDER_CHANGE_LISTENER)
    fun setSliderChangeListener(
        slider: Slider,
        listener: Slider.OnChangeListener?
    ) {
        if (listener != null) {
            slider.addOnChangeListener(listener)
        }
    }

}