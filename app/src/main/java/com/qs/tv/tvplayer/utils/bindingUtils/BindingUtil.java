package com.qs.tv.tvplayer.utils.bindingUtils;

import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.BindingAdapter;

public class BindingUtil {

    private static final String SET_SWITCH_BUTTON_LISTENER = "setSwitchButtonListener";
    private static final String SET_SWITCH_BUTTON_CHECKED = "setSwitchButtonChecked";
    private static final String SET_ON_CHECKED_CHANGED = "onCheckedChanged";
    private static final String SET_ON_RADIO_BUTTON_LISTENER = "onRadioButtonListener";
    private static final String SET_CHECKBOX_CHECKED = "checkboxChecked";
    private static final String SET_ON_CHECKED_CHANGED_RADIO_GROUP = "onCheckedChangedRadioGroup";
    private static final String SET_RADIO_BUTTON_CHECKED = "radioButtonChecked";
    private static final String SET_SPANNABLE_STRING = "setSpannableString";
    private static final String IS_CHECK_BOX_CHECKED = "isCheckBoxChecked";
    private static final String SET_SPINNER_ARRAY_ADAPTER = "setSpinnerArrayAdapter";
    private static final String SET_MARGIN_TOP = "setMarginTop";
    private static final String SET_MARGIN_BOTTOM = "setMarginBottom";
    private static final String SET_MARGIN_START = "setMarginStart";
    private static final String SET_MARGIN_END = "setMarginEnd";

    @BindingAdapter({SET_SWITCH_BUTTON_LISTENER})
    public static void setOnCheckedChangeListener(SwitchCompat switchButton,
                                                  CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (switchButton != null && onCheckedChangeListener != null) {
            switchButton.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @BindingAdapter({SET_SWITCH_BUTTON_CHECKED})
    public static void setSwitchButtonChecked(SwitchCompat switchButton,
                                              boolean checked) {
        if (switchButton != null) {
            switchButton.setChecked(checked);
        }
    }

    @BindingAdapter({SET_ON_CHECKED_CHANGED})
    public static void onCheckedChanged(CheckBox checkBox,
                                        CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (onCheckedChangeListener != null) {
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @BindingAdapter({SET_CHECKBOX_CHECKED})
    public static void checkboxChecked(CheckBox checkBox, boolean isChecked) {
        checkBox.setChecked(isChecked);
    }

    @BindingAdapter({SET_ON_RADIO_BUTTON_LISTENER})
    public static void onRadioButtonListener(RadioButton radioButton,
                                             RadioButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (onCheckedChangeListener != null) {
            radioButton.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @BindingAdapter({SET_RADIO_BUTTON_CHECKED})
    public static void radioButtonChecked(RadioButton radioButton, boolean isChecked) {
        radioButton.setChecked(isChecked);
    }

    @BindingAdapter({IS_CHECK_BOX_CHECKED})
    public static void isCheckBoxChecked(@NonNull CheckBox checkBox, boolean isChecked) {
        checkBox.setChecked(isChecked);
    }

    @BindingAdapter({SET_ON_CHECKED_CHANGED_RADIO_GROUP})
    public static void onCheckedChangedRadioGroup(RadioGroup radioGroup,
                                                  RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        if (onCheckedChangeListener != null) {
            radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    @BindingAdapter({SET_SPANNABLE_STRING})
    public static void setSpannableString(TextView textView,
                                          SpannableString spannableString) {
        if (spannableString != null) {
            textView.setText(spannableString);
        }
    }

    @BindingAdapter({SET_SPINNER_ARRAY_ADAPTER})
    public static <T> void setSpinnerArrayAdapter(@NonNull Spinner spinner,
                                                  ArrayAdapter<T> arrayAdapter) {
        if (arrayAdapter != null) {
            spinner.setAdapter(arrayAdapter);
        }
    }

    @BindingAdapter(value = {SET_MARGIN_TOP, SET_MARGIN_BOTTOM, SET_MARGIN_START, SET_MARGIN_END}, requireAll = true)
    public static void setMargins(@NonNull View view, int top, int bottom, int start, int end) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p instanceof CoordinatorLayout.LayoutParams lp) {
            lp.setMargins(start, top, end, bottom);
            view.setLayoutParams(lp);
        }
    }

}
