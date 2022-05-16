package com.app.xdcpay.Activities.Settings;

import static com.app.xdcpay.Utils.AppUtility.showSnackBar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

import com.app.xdcpay.Activities.Networks.NetworkDetailsActivity;
import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewBold;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;

public class AdvanceSettings extends BaseActivity {
    private View view;
    private TextViewBold tvDownloadLogs, btn_ResetAccount;
    private SwitchCompat switch_btn_gas;
    private TextView tv_gas_switch;
    private TextViewMedium title;
    NetworkDataBase networkDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_settings);
        view = findViewById(android.R.id.content);
    }

    @Override
    public void getId() {
        tvDownloadLogs = findViewById(R.id.tvDownloadLogs);
        btn_ResetAccount = findViewById(R.id.btn_ResetAccount);
        switch_btn_gas = findViewById(R.id.switch_btn_gas);
        tv_gas_switch = findViewById(R.id.tv_gas_switch);
        title = findViewById(R.id.title);
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        tvDownloadLogs.setOnClickListener(this);
        btn_ResetAccount.setOnClickListener(this);

        switch_btn_gas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    tv_gas_switch.setText(getString(R.string.text_on));
                else
                    tv_gas_switch.setText(getString(R.string.text_off));
            }
        });
        setData();
    }

    @Override
    public void setData() {
        networkDataBase = NetworkDataBase.getInstance(AdvanceSettings.this);
        title.setText(getString(R.string.advance_settings));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tvDownloadLogs:
                showSnackBar(getApplicationContext(), getString(R.string.under_development), view);
                break;

            case R.id.btn_ResetAccount:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_reset_account);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextViewMedium tvCancel = dialog.findViewById(R.id.tvCancel);
                TextViewMedium tv_reset = dialog.findViewById(R.id.tv_reset);

                tv_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        new resetTransaction(AdvanceSettings.this).execute();
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
//                showSnackBar(getApplicationContext(), getString(R.string.under_development), view);
                break;
        }
    }

    private class resetTransaction extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<AdvanceSettings> activityReference;
//            private int networkId;

        public resetTransaction(AdvanceSettings addNetworkActivity) {
            activityReference = new WeakReference<>(addNetworkActivity);
//                this.networkId = networkId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getTransactionsDao().deleteTransaction();
            Intent i = new Intent(AdvanceSettings.this, NetworksActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }
}
