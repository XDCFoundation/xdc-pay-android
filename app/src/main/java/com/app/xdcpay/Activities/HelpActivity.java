package com.app.xdcpay.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class HelpActivity extends BaseActivity {
    private TextViewMedium title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    public void getId() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.info_help));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}