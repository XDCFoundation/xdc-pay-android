package com.app.xdcpay.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by parangat on 26/3/19.
 */

public class EditTextBold extends AppCompatEditText {
    public EditTextBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public EditTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public EditTextBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Avenir-Medium.ttf", context);
        setTypeface(customFont);
    }
}
