package com.app.xdcpay.Interface;

import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public interface AccountCallbackInterface {
    void onAccountClickListener(Context context, int id, BottomSheetDialog bottomSheetDialog);
}
