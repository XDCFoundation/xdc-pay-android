package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.app.xdcpay.Activities.CreateWallet.ConfirmSeedPhraseActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.Validations;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReadWalletDetails readWalletDetails = new ReadWalletDetails(SplashActivity.this);
        if (readWalletDetails.getIsLogin())
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        else if (Validations.hasText(readWalletDetails.getAccountAddress()))
            if (readWalletDetails.getIsSeedPhraseConfirm())
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            else
                startActivity(new Intent(SplashActivity.this, ConfirmSeedPhraseActivity.class));
        else
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        finish();
    }
}
