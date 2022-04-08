package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class ConfirmTransactionActivity extends BaseActivity {
    private TextViewMedium btnConfirm;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_transaction);
    }

    @Override
    public void getId() {
        btnConfirm = findViewById(R.id.btnConfirm);
        iv_back = findViewById(R.id.iv_back);
        setData();
    }

    @Override
    public void setListener() {
        btnConfirm.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                Intent i = new Intent(ConfirmTransactionActivity.this, SentActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.iv_back:
               onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConfirmTransactionActivity.this, SendActivity.class);
        startActivity(i);
        finish();
    }
}