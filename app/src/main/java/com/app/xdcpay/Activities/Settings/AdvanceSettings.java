package com.app.xdcpay.Activities.Settings;

import static com.app.xdcpay.Utils.AppUtility.showSnackBar;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewBold;
import com.app.xdcpay.Views.TextViewMedium;

public class AdvanceSettings extends BaseActivity {
    private View view;
    private TextViewBold tvDownloadLogs, btn_ResetAccount;
    private SwitchCompat switch_btn_gas;
    private TextView tv_gas_switch;
    private TextViewMedium title;

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
//                showSnackBar(getApplicationContext(), getString(R.string.under_development), view);
                break;
        }
    }
}
