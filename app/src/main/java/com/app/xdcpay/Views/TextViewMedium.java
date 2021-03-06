package com.app.xdcpay.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TextViewMedium extends AppCompatTextView {
    public TextViewMedium(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Inter-Medium.ttf", context);
        setTypeface(customFont);
    }
}
