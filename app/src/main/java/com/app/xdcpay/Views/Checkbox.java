package com.app.xdcpay.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class Checkbox extends AppCompatCheckBox {
    public Checkbox(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public Checkbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Inter-Regular.ttf", context);
        setTypeface(customFont);
    }
}
