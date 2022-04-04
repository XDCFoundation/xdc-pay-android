package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

public class AddNetworkActivity extends BaseActivity {
    private AppCompatButton btn_addNetwork;
    private ImageView back;
    private TextViewMedium title;
    private EditText etNetworkName, etRPCUrl, etChainId, etCurrencySymbol, etBlockExplorer;
    private String str_currencySymbol, str_blockExplorer;

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
    }

    @Override
    public void setListener() {
        btn_addNetwork.setOnClickListener(this);
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
            case R.id.btn_addNetwork:
                if (isValid()) {
                    if (etCurrencySymbol.getText().toString().isEmpty()) {
                        str_currencySymbol = "";
                    } else if (etBlockExplorer.getText().toString().isEmpty()) {
                        str_blockExplorer = "";
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNetworkActivity.this, NetworksActivity.class);
        startActivity(intent);
        finish();
//        super.onBackPressed();
    }

    private boolean isValid() {
        if (!Validations.hasText(etNetworkName))
            etNetworkName.setError(getResources().getString(R.string.error_empty));
        else if (!Validations.hasText(etRPCUrl))
            etRPCUrl.setError(getResources().getString(R.string.error_password_empty));
        else if (!Validations.hasText(etChainId))
            etChainId.setError(getResources().getString(R.string.error_password_empty));
        else return true;

        return false;
    }
}