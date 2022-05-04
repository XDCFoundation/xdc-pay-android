package com.app.xdcpay.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.BuildConfig;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewMedium;

public class HelpActivity extends BaseActivity {
    private TextViewMedium title, tvVersion;
    private ImageView back;
    private TextView privacyPolicy, support_center, contactUs, visitOurWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    public void getId() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        privacyPolicy = findViewById(R.id.privacyPolicy);
        support_center = findViewById(R.id.support_center);
        contactUs = findViewById(R.id.contactUs);
        visitOurWebsite = findViewById(R.id.visitOurWebsite);
        tvVersion = findViewById(R.id.tvVersion);
        setData();
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        privacyPolicy.setOnClickListener(this);
        support_center.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        visitOurWebsite.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.info_help));
        tvVersion.setText("" + (BuildConfig.VERSION_NAME));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.privacyPolicy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PRIVACY_POLICY_URL)));
                break;
            case R.id.support_center:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SUPPORT_CENTER_URL)));
                break;
            case R.id.contactUs:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CONTACT_US_URL)));
                break;
            case R.id.visitOurWebsite:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEBSITE_URL)));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}