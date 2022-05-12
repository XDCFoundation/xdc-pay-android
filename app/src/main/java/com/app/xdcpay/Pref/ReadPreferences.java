package com.app.xdcpay.Pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.xdcpay.Utils.Constants;

public class ReadPreferences {
    String myprefs = "MyPrefs";
    int mode = Activity.MODE_PRIVATE;
    Context ctx;
    String res = "";
    private SharedPreferences prefs;

    public ReadPreferences(Context context) {
        this.ctx = context;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
    }

    public String getSelectedCurrency() {
        res = "";
        res = prefs.getString("selectedCurrency", "");
        return res;
    }

    public String getNetworkName() {
        String res;
        res = prefs.getString("NetworkPref", Constants.MAIN_NET_NAME);
        return res;
    }
}
