package com.app.xdcpay.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;

public class BuyActivity extends BaseActivity {
    private TextViewMedium tvSimplex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
    }

    @Override
    public void getId() {
        tvSimplex = findViewById(R.id.tvSimplex);
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        tvSimplex.setOnClickListener(this);
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
            case R.id.tvSimplex :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SIMPLEX_URL)));
                break;
        }
    }
}
