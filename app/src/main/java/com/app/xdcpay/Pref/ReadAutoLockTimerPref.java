package com.app.xdcpay.Pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ReadAutoLockTimerPref {
    String myprefs = "AutoLockTimer";
    int mode = Activity.MODE_PRIVATE;
    Context ctx;
    private SharedPreferences prefs;

    public ReadAutoLockTimerPref(Context context) {
        this.ctx = context;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
    }

    public int getTimer() {
        int res = 0;
        res = prefs.getInt("timer", 5);
        return res;
    }

    public boolean getHideToken() {
        boolean res = false;
        res = prefs.getBoolean("hide_token", false);
        return res;
    }

}
