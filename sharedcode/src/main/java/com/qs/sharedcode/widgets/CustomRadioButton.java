package com.qs.sharedcode.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.qs.sharedcode.R;
import com.qs.sharedcode.utils.UIUtils;

public class CustomRadioButton extends AppCompatRadioButton {

    public CustomRadioButton(Context context) {
        super(context);
        init(context, null);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.CustomRadioButton, 0, 0);
            if (typedArray != null) {
                setFont(typedArray);
            }
        }
    }

    private void setFont(TypedArray typedArray) {
        try {
            int textFont = typedArray.getInteger(R.styleable.CustomRadioButton_text_font, 0);
            setTypeface(UIUtils.getFont(getContext(), textFont));
        } finally {
            typedArray.recycle();
        }
    }
}
