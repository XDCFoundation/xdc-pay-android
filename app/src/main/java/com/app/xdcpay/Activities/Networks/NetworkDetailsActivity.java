package com.app.xdcpay.Activities.Networks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class NetworkDetailsActivity extends BaseActivity {
    private ImageView back;
    private TextViewMedium title;
    private TextViewMedium tvNetworkName, tvRPCUrl, tvChainId, tvCurrencySymbol, tvBlockExplorer;
    private String str_currencySymbol, str_blockExplorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_details);
    }

    @Override
    public void getId() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        tvNetworkName = findViewById(R.id.tvNetworkName);
        tvRPCUrl = findViewById(R.id.tvRPCUrl);
        tvChainId = findViewById(R.id.tvChainId);
        tvCurrencySymbol = findViewById(R.id.tvCurrencySymbol);
        tvBlockExplorer = findViewById(R.id.tvBlockExplorer);
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NetworkDetailsActivity.this, NetworksActivity.class);
        startActivity(intent);
        finish();
    }
}