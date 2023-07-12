package com.qs.sharedcode.widgets.material;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.chip.Chip;
import com.qs.sharedcode.R;
import com.qs.sharedcode.utils.UIUtils;

public class CustomMaterialChip extends Chip {

    public CustomMaterialChip(Context context) {
        super(context);
        init(context, null);
    }

    public CustomMaterialChip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomMaterialChip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.CustomMaterialChip, 0, 0);
            if (typedArray != null) {
                setFont(typedArray);
            }
        }
    }

    private void setFont(TypedArray typedArray) {
        try {
            int textFont = typedArray.getInteger(R.styleable.CustomMaterialChip_text_font, 0);
            setTypeface(UIUtils.getFont(getContext(), textFont));
        } finally {
            typedArray.recycle();
        }
    }

    public void text_font(int textFont) {
        setTypeface(UIUtils.getFont(getContext(), textFont));
    }

}
