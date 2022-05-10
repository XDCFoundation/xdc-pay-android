package com.app.xdcpay.Pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveAutoLockTimerPref {
    String myprefs = "AutoLockTimer";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    Context ctx;
    private SharedPreferences prefs;

    public SaveAutoLockTimerPref(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(myprefs, mode);
    }

    public void clearWalletDetails(Context ctx) {
        prefs = ctx.getSharedPreferences(myprefs, mode);
        prefs.edit().clear().commit();
    }

    public void saveTime(int s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("timer", s);
        result = editor.commit();
    }
}
