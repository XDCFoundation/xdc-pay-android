package com.app.xdcpay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class SettingsActivity extends BaseActivity {
    private TextViewMedium tv_Networks, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        title.setText(getString(R.string.settings));
    }

    @Override
    public void getId() {
        tv_Networks = findViewById(R.id.tv_Networks);
        title = findViewById(R.id.title);

    }

    @Override
    public void setListener() {
        tv_Networks.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Networks:
                Intent intent = new Intent(SettingsActivity.this, NetworksActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
