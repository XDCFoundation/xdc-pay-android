package com.app.xdcpay.Activities.Networks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class NetworkDetailsActivity extends BaseActivity {
    public static String NETWORK_NAME = "NETWORK_NAME";
    public static String NETWORK_RPC_URL = "NETWORK_URL";
    public static String CHAIN_ID = "CHAIN_ID";
    public static String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";
    public static String BLOCK_EXPLORE_URL = "BLOCK_EXPLORER_URL";
    private ImageView back;
    private TextViewMedium title;
    private TextViewMedium tvXDCTitle, tvNetworkName, tvRPCUrl, tvChainId, tvCurrencySymbol, tvBlockExplorer;
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
        tvXDCTitle = findViewById(R.id.tvXDCTitle);
        tvRPCUrl = findViewById(R.id.tvRPCUrl);
        tvChainId = findViewById(R.id.tvChainId);
        tvCurrencySymbol = findViewById(R.id.tvCurrencySymbol);
        tvBlockExplorer = findViewById(R.id.tvBlockExplorer);
        setData();
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null){
            tvXDCTitle.setText(getString(R.string.txt_xdc)+" "+intent.getStringExtra(NETWORK_NAME));
            tvNetworkName.setText(intent.getStringExtra(NETWORK_NAME));
            tvRPCUrl.setText(intent.getStringExtra(NETWORK_RPC_URL));
            tvChainId.setText(intent.getStringExtra(CHAIN_ID));
            tvCurrencySymbol.setText(intent.getStringExtra(CURRENCY_SYMBOL));
            tvBlockExplorer.setText(intent.getStringExtra(BLOCK_EXPLORE_URL));
        }
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