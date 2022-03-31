package com.app.xdcpay.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by parangat on 10/1/19.
 */

public class TextView extends AppCompatTextView {
    public TextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Inter-Regular.ttf", context);
        setTypeface(customFont);
    }
}
