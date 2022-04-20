package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.Activities.Contacts.ContactsActivity;
import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.Activities.SecurityPrivacy.SecurityAndPrivacyActivity;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class SettingsActivity extends BaseActivity {
    private TextViewMedium tv_Networks, title, tv_Contacts, security_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void getId() {
        tv_Networks = findViewById(R.id.tv_Networks);
        title = findViewById(R.id.title);
        tv_Contacts = findViewById(R.id.tv_Contacts);
        security_privacy = findViewById(R.id.security_privacy);

        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.genral).setOnClickListener(this);
        findViewById(R.id.advance).setOnClickListener(this);
//        findViewById(R.id.security).setOnClickListener(this);
//        findViewById(R.id.networks).setOnClickListener(this);
//        findViewById(R.id.contacts).setOnClickListener(this);
        tv_Networks.setOnClickListener(this);
        tv_Contacts.setOnClickListener(this);
        security_privacy.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getResources().getString(R.string.settings));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Networks:
                Intent intent = new Intent(SettingsActivity.this, NetworksActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_Contacts:
                Intent intentContact = new Intent(SettingsActivity.this, ContactsActivity.class);
                startActivity(intentContact);
                finish();
                break;
            case R.id.security_privacy:
                Intent intentSecurity = new Intent(SettingsActivity.this, SecurityAndPrivacyActivity.class);
                startActivity(intentSecurity);
                finish();
                break;
        }
    }

}

