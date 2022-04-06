package com.app.xdcpay.Activities.SecurityPrivacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewBold;
import com.app.xdcpay.Views.TextViewMedium;

public class SecurityAndPrivacyActivity extends BaseActivity {
    private TextViewBold btn_revealSeedPhrase, btn_changePassword, btn_showPrivateKey;
    private SwitchCompat switch_btn;
    private TextView tv_switch;
    private TextViewMedium title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_and_privacy);
    }

    @Override
    public void getId() {
        btn_revealSeedPhrase = findViewById(R.id.btn_revealSeedPhrase);
        btn_changePassword = findViewById(R.id.btn_changePassword);
        btn_showPrivateKey = findViewById(R.id.btn_showPrivateKey);
        switch_btn = findViewById(R.id.switch_btn);
        tv_switch = findViewById(R.id.tv_switch);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        setData();
    }

    @Override
    public void setListener() {
        btn_revealSeedPhrase.setOnClickListener(this);
        btn_changePassword.setOnClickListener(this);
        btn_showPrivateKey.setOnClickListener(this);
        btn_showPrivateKey.setOnClickListener(this);
        back.setOnClickListener(this);

        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    tv_switch.setText(getString(R.string.text_on));
                else
                    tv_switch.setText(getString(R.string.text_off));
            }
        });
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.security_privacy_settings));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_revealSeedPhrase:
                Intent i = new Intent(SecurityAndPrivacyActivity.this, SeedPhrasePasswordActivity.class);
                startActivity(i);
                break;
            case R.id.btn_changePassword:
                Intent intent = new Intent(SecurityAndPrivacyActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_showPrivateKey:
                Intent privateKeyIntent = new Intent(SecurityAndPrivacyActivity.this, PrivateKeyActivity.class);
                startActivity(privateKeyIntent);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}