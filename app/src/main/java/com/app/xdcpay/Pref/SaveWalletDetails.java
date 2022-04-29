package com.app.xdcpay.Pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveWalletDetails {
    String myprefs = "WalletDetails";
    int mode = Activity.MODE_PRIVATE;
    boolean result = false;
    Context ctx;
    private SharedPreferences prefs;

    public SaveWalletDetails(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(myprefs, mode);
    }

    public void clearWalletDetails(Context ctx) {
        prefs = ctx.getSharedPreferences(myprefs, mode);
        prefs.edit().clear().commit();
    }

    public void savePrivateKey(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("private_key", s);
        result = editor.commit();

    }

    public void savePublicKey(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("public_key", s);
        result = editor.commit();

    }

    public void saveAccountAddress(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("account_address", s);
        result = editor.commit();

    }

    public void saveSeedPhrase(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("seed_phrase", s);
        result = editor.commit();

    }

    public void savePassword(String s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", s);
        result = editor.commit();

    }

    public void saveIsLogin(boolean s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_login", s);
        result = editor.commit();

    }

    public void IsSeedPhaseConfirm(boolean s) {
        result = false;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isSeedPhraseConfirm", s);
        result = editor.commit();

    }

}
