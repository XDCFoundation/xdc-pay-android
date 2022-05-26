package com.app.xdcpay.Activities.SecurityPrivacy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.Activities.LoginActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewMedium;

public class ChangePasswordActivity extends BaseActivity {
    EditText et_password;
    TextViewMedium confirmBtn, title;
    ImageView back;
    ReadWalletDetails readWalletDetails;
    private TextView tvPasswordErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    @Override
    public void getId() {
        readWalletDetails = new ReadWalletDetails(ChangePasswordActivity.this);
        et_password = findViewById(R.id.et_password);
        confirmBtn = findViewById(R.id.confirmBtn);
        tvPasswordErr = findViewById(R.id.tvPasswordErr);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {
        confirmBtn.setOnClickListener(this);
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
        title.setText(getString(R.string.change_password));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmBtn:
                if (isValid())
                    if (et_password.getText().toString().equals(readWalletDetails.getPassword())) {
                        Intent i = new Intent(ChangePasswordActivity.this, ChangePasswordScreenActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    } else
                        tvPasswordErr.setVisibility(View.VISIBLE);
                tvPasswordErr.setText(getString(R.string.error_incorrect_password));
//                        et_password.setError(getResources().getString(R.string.error_incorrect_password));
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(et_password)) {
            tvPasswordErr.setVisibility(View.VISIBLE);
            tvPasswordErr.setText(getString(R.string.error_password_empty));
//            et_password.setError(getResources().getString(R.string.error_password_empty));
        } else
            return true;

        return false;
    }
}