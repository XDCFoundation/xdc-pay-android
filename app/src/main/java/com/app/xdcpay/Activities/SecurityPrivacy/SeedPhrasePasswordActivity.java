package com.app.xdcpay.Activities.SecurityPrivacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.Activities.ImportWalletActivity;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

public class SeedPhrasePasswordActivity extends BaseActivity {
    EditText et_password;
    TextViewMedium reveal_tv,title;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_phrase_password);
    }

    @Override
    public void getId() {
        et_password = findViewById(R.id.et_password);
        reveal_tv = findViewById(R.id.reveal_tv);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {
        reveal_tv.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.secret_seed));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reveal_tv:
                if (isValid()) {
                    Intent i = new Intent(SeedPhrasePasswordActivity.this, RevealSeedPhraseActivity.class);
                    startActivity(i);
                    break;
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(et_password))
            et_password.setError(getResources().getString(R.string.error_password_empty));
        else return true;

        return false;
    }
}