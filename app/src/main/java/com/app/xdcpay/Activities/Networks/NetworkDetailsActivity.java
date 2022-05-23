package com.app.xdcpay.Activities.Networks;

import static com.app.xdcpay.Utils.Constants.BLOCK_EXPLORE_URL;
import static com.app.xdcpay.Utils.Constants.CHAIN_ID;
import static com.app.xdcpay.Utils.Constants.CURRENCY_SYMBOL;
import static com.app.xdcpay.Utils.Constants.NETWORK_CAN_DELETE;
import static com.app.xdcpay.Utils.Constants.NETWORK_ID;
import static com.app.xdcpay.Utils.Constants.NETWORK_NAME;
import static com.app.xdcpay.Utils.Constants.NETWORK_RPC_URL;
import static com.app.xdcpay.Utils.Constants.NO;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Pref.SavePreferences;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NetworkDetailsActivity extends BaseActivity {
    private ImageView back;
    private TextViewMedium title;
    private TextViewMedium tvXDCTitle, tvNetworkName, tvRPCUrl, tvChainId, tvCurrencySymbol, tvBlockExplorer, tvRemove;
    private String str_currencySymbol, str_blockExplorer;
    NetworkDataBase networkDataBase;
    private int networkId = 0;
    SavePreferences savePreferences;

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
        tvRemove = findViewById(R.id.tvRemove);
        savePreferences = new SavePreferences(NetworkDetailsActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        tvRemove.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkDataBase = NetworkDataBase.getInstance(NetworkDetailsActivity.this);
        Intent intent = getIntent();
        if (intent != null) {
            tvXDCTitle.setText(getString(R.string.txt_xdc) + " " + intent.getStringExtra(NETWORK_NAME));
            tvNetworkName.setText(intent.getStringExtra(NETWORK_NAME));
            tvRPCUrl.setText(intent.getStringExtra(NETWORK_RPC_URL));
            tvChainId.setText(intent.getStringExtra(CHAIN_ID));
            tvCurrencySymbol.setText(intent.getStringExtra(CURRENCY_SYMBOL));
            tvBlockExplorer.setText(intent.getStringExtra(BLOCK_EXPLORE_URL));
            networkId = intent.getIntExtra(NETWORK_ID, 0);
            if (intent.getStringExtra(NETWORK_CAN_DELETE).equals(NO))
                tvRemove.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tvRemove:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        new InsertTask(NetworkDetailsActivity.this, networkId).execute();
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<NetworkDetailsActivity> activityReference;
        private int networkId;

        public InsertTask(NetworkDetailsActivity addNetworkActivity, int networkId) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.networkId = networkId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getDatabaseDao().deleteById(networkId);
            savePreferences.saveNetwork(getString(R.string.xdc_mainnet));
            Intent i = new Intent(NetworkDetailsActivity.this, NetworksActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NetworkDetailsActivity.this, NetworksActivity.class);
        startActivity(intent);
        finish();
    }
}