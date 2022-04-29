package com.app.xdcpay.Activities.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;

public class AdvanceSettings extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_settings);
    }

    @Override
    public void getId() {

    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
