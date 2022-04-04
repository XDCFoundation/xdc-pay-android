package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;

public class WalletSetupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_setup);
    }

    @Override
    public void getId() {

    }

    @Override
    public void setListener() {
        findViewById(R.id.create_wallet).setOnClickListener(this);
        findViewById(R.id.import_wallet).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.import_wallet:
// changed for testing
                startActivity(new Intent(WalletSetupActivity.this, ImportWalletActivity.class));
//                startActivity(new Intent(WalletSetupActivity.this, HomeActivity.class));
                break;

            case R.id.create_wallet:
                startActivity(new Intent(WalletSetupActivity.this, CreateWalletActivity.class));
                break;
        }
    }
}
