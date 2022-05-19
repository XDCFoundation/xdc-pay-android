package com.app.xdcpay.Activities.Networks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.Activities.SendActivity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;

public class AddNetworkActivity extends BaseActivity {
    private AppCompatButton btn_addNetwork;
    private ImageView back;
    private TextViewMedium title;
    private EditText etNetworkName, etRPCUrl, etChainId, etCurrencySymbol, etBlockExplorer;
    private String str_currencySymbol = "", str_blockExplorer = "";
    NetworkEntity networkEntity;
    NetworkDataBase networkDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_network);
    }

    @Override
    public void getId() {
        btn_addNetwork = findViewById(R.id.btn_addNetwork);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        etNetworkName = findViewById(R.id.etNetworkName);
        etRPCUrl = findViewById(R.id.etRPCUrl);
        etChainId = findViewById(R.id.etChainId);
        etCurrencySymbol = findViewById(R.id.etCurrencySymbol);
        etBlockExplorer = findViewById(R.id.etBlockExplorer);
        title.setText(getString(R.string.add_networks));
        setData();
    }

    @Override
    public void setListener() {
        btn_addNetwork.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkDataBase = NetworkDataBase.getInstance(AddNetworkActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_addNetwork:
                if (isValid()) {
                    str_currencySymbol = etCurrencySymbol.getText().toString();
                    str_blockExplorer = etBlockExplorer.getText().toString();
                    if (etCurrencySymbol.getText().toString().isEmpty()) {
                        str_currencySymbol = "";
                    } else if (etBlockExplorer.getText().toString().isEmpty()) {
                        str_blockExplorer = "";
                    }
                    networkEntity = new NetworkEntity(
                            etNetworkName.getText().toString(),
                            etRPCUrl.getText().toString(),
                            etChainId.getText().toString(),
                            str_currencySymbol, str_blockExplorer);
                    new InsertTask(AddNetworkActivity.this, networkEntity).execute();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNetworkActivity.this, NetworksActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isValid() {
        if (!Validations.hasText(etNetworkName))
            etNetworkName.setError(getResources().getString(R.string.network_name_error));
        else if (!Validations.hasText(etRPCUrl))
            etRPCUrl.setError(getResources().getString(R.string.rpc_url_error));
        else if (!Validations.isValidUrl(etRPCUrl.getText().toString()))
            etRPCUrl.setError(getResources().getString(R.string.rpc_url_error));
        else if (!Validations.hasText(etChainId))
            etChainId.setError(getResources().getString(R.string.chain_id_error));
        else return true;

        return false;
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<AddNetworkActivity> activityReference;
        private NetworkEntity networkEntity;

        public InsertTask(AddNetworkActivity addNetworkActivity, NetworkEntity networkEntity) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.networkEntity = networkEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getDatabaseDao().insertNetwork(networkEntity);
            startActivity(new Intent(AddNetworkActivity.this, NetworksActivity.class));
            finish();
            return null;
        }
    }
}