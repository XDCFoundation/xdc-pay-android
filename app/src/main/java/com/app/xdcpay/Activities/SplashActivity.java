package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.app.xdcpay.Adapters.WelcomePagerAdapter;
import com.app.xdcpay.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        finish();
    }
}
