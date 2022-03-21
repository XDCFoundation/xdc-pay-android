package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;

public class ImportWalletActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
    }

    @Override
    public void getId() {

    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.import_tv).setOnClickListener(this);
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

            case R.id.import_tv:
                Intent intent = new Intent(ImportWalletActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
