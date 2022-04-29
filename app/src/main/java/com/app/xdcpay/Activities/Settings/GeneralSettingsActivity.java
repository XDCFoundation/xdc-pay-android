package com.app.xdcpay.Activities.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class GeneralSettingsActivity extends BaseActivity {
    private TextViewMedium title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setData() {
        title.setText(getString(R.string.general_settings));
    }

    @Override
    public void onClick(View v) {

    }
}