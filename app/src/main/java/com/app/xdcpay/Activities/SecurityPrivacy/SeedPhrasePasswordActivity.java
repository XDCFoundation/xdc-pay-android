package com.app.xdcpay.Activities.SecurityPrivacy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewMedium;

public class SeedPhrasePasswordActivity extends BaseActivity {
    EditText et_password;
    TextViewMedium reveal_tv, title;
    ImageView back;
    private ReadWalletDetails readWalletDetails;
    private TextView tvPasswordErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_phrase_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void getId() {
        readWalletDetails = new ReadWalletDetails(SeedPhrasePasswordActivity.this);
        et_password = findViewById(R.id.et_password);
        reveal_tv = findViewById(R.id.reveal_tv);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        tvPasswordErr = findViewById(R.id.tvPasswordErr);
        setData();
    }

    @Override
    public void setListener() {
        reveal_tv.setOnClickListener(this);
        back.setOnClickListener(this);

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvPasswordErr.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void setData() {
        title.setText(getString(R.string.secret_seed));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reveal_tv:
                if (isValid())
                    if (et_password.getText().toString().equals(readWalletDetails.getPassword())) {
                        Intent i = new Intent(SeedPhrasePasswordActivity.this, RevealSeedPhraseActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    } else {
                        tvPasswordErr.setVisibility(View.VISIBLE);
                        tvPasswordErr.setText(getResources().getString(R.string.error_password_not_match));
//                        Toast.makeText(this, getResources().getString(R.string.error_password_not_match), Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(et_password)) {
//            et_password.setError(getResources().getString(R.string.error_password_empty));
            tvPasswordErr.setVisibility(View.VISIBLE);
            tvPasswordErr.setText(getResources().getString(R.string.error_password_empty));
        }
        else return true;

        return false;
    }
}