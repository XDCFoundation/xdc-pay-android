package com.app.xdcpay.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;

public class SettingsActivity extends BaseActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);

        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.genral).setOnClickListener(this);
        findViewById(R.id.advance).setOnClickListener(this);
        findViewById(R.id.security).setOnClickListener(this);
        findViewById(R.id.networks).setOnClickListener(this);
        findViewById(R.id.contacts).setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getResources().getString(R.string.settings));
    }

    @Override
    public void onClick(View v) {

    }
}
