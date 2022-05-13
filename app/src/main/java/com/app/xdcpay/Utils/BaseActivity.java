package com.app.xdcpay.Utils;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.xdcpay.Activities.Accounts.ImportAccountActivity;
import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.Activities.SplashActivity;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Pref.ReadAutoLockTimerPref;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.Pref.SharedPreferenceHelper;
import com.ybs.passwordstrengthmeter.PasswordStrength;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public NetworkDataBase networkDataBase;
    public abstract void getId();

    public abstract void setListener();

    public abstract void setData();

    public abstract void onClick(View v);

    Handler handler;
    Runnable r;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getId();
        setListener();

        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                stopHandler();
                ReadWalletDetails readWalletDetails = new ReadWalletDetails(BaseActivity.this);
                if (readWalletDetails.getIsLogin()) {
                    SaveWalletDetails saveWalletDetails = new SaveWalletDetails(BaseActivity.this);
                    saveWalletDetails.saveIsLogin(false);
                    Intent intent = new Intent(BaseActivity.this, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        };
        startHandler();
//
//
//        if (readWalletDetails.getIsLogin())
//            startHandler();
//        else
//            stopHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        boolean isInForeground = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        if (!isInForeground)
            stopHandler();
    }

    public void showtoast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updatePasswordStrengthView(EditText password, ProgressBar progressBar) {
        if (password.getText().toString().isEmpty()) {
            progressBar.setProgress(0);
            return;
        }
        PasswordStrength str = PasswordStrength.calculateStrength(password.getText().toString());
        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(BaseActivity.this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(BaseActivity.this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(BaseActivity.this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
        stopHandler();//stop first and then start
        ReadWalletDetails readWalletDetails = new ReadWalletDetails(BaseActivity.this);
        if (readWalletDetails.getIsLogin()) {
            startHandler();
        }
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        ReadAutoLockTimerPref readAutoLockTimer = new ReadAutoLockTimerPref(BaseActivity.this);
        handler.postDelayed(r, readAutoLockTimer.getTimer() * 1000);
    }

    public AccountEntity getselectedaccount() {
        AccountEntity account =  NetworkDataBase.getInstance(BaseActivity.this).getAccountDao().getAccountList().get(Integer.parseInt(SharedPreferenceHelper.getSharedPreferenceString(BaseActivity.this, Constants.ACCOUNT, "")));
        return  account;
    }

}
