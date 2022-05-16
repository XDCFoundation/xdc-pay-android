package com.app.xdcpay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;

public class AddTokenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_token);
    }

    @Override
    public void getId() {

    }

    @Override
    public void setListener() {
        findViewById(R.id.add).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                finish();
                break;
        }
    }
}
