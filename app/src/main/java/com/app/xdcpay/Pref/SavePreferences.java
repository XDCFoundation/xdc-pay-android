package com.app.xdcpay.Pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SavePreferences {
    String myprefs = "MyPrefs";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    Context ctx;
    private SharedPreferences prefs;

    public SavePreferences(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(myprefs, mode);
    }

    public void saveSelectedCurrency(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selectedCurrency", s);
        result = editor.commit();

    }

    public void saveNetwork(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("NetworkPref", s);
        result = editor.commit();
    }

    public void setIsGasPriceSelected(boolean s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("GasPriceSelected", s);
        result = editor.commit();
    }

}
