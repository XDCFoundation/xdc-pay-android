package com.app.xdcpay.Activities;

import static com.app.xdcpay.Utils.Constants.MAIN_NET_NAME;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.xdcpay.Pref.ReadPreferences;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;

public class BuyActivity extends BaseActivity {
    private TextView tvSimplex, tvFaucet, tvTitle;
    ReadPreferences readNetworkPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
    }

    @Override
    public void getId() {
        tvSimplex = findViewById(R.id.tvSimplex);
        tvFaucet = findViewById(R.id.tvFaucet);
        tvTitle = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        tvSimplex.setOnClickListener(this);
        tvFaucet.setOnClickListener(this);
    }

    @Override
    public void setData() {
        tvTitle.setText(R.string.buy);
        readNetworkPref = new ReadPreferences(BuyActivity.this);
        if (readNetworkPref.getNetworkName().equals(MAIN_NET_NAME)) {
            tvFaucet.setVisibility(View.GONE);
            tvSimplex.setVisibility(View.VISIBLE);
        } else {
            tvFaucet.setVisibility(View.VISIBLE);
            tvSimplex.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
              onBackPressed();
                break;
            case R.id.tvSimplex:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SIMPLEX_URL)));
                break;
            case R.id.tvFaucet:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APOTHEM_NET_URL)));
                break;
        }
    }

    @Override
    public void onBackPressed() {
       Intent i = new Intent(BuyActivity.this,HomeActivity.class);
       startActivity(i);
       finish();
    }
}
