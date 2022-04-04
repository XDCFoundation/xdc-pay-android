package com.app.xdcpay.Pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ReadWalletDetails {
    String myprefs = "WalletDetails";
    int mode = Activity.MODE_PRIVATE;
    Context ctx;
    String res = "";
    private SharedPreferences prefs;

    public ReadWalletDetails(Context context) {
        this.ctx = context;
        prefs = this.ctx.getSharedPreferences(myprefs, mode);
    }

    public String getPrivateKey() {
        res = "";
        res = prefs.getString("private_key", "");
        return res;
    }

    public String getPublicKey() {
        res = "";
        res = prefs.getString("public_key", "");
        return res;
    }

    public String getAccountAddress() {
        res = "";
        res = prefs.getString("account_address", "");
        return res;
    }

    public String getSeedPhrase() {
        res = "";
        res = prefs.getString("seed_phrase", "");
        return res;
    }

    public String getPassword() {
        res = "";
        res = prefs.getString("password", "");
        return res;
    }

    public boolean getIsLogin() {
        boolean res;
        res = prefs.getBoolean("is_login", false);
        return res;
    }
}
