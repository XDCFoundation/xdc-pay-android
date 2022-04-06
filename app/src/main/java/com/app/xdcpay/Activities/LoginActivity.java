package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;

public class LoginActivity extends BaseActivity {
    private EditText password;
    private ReadWalletDetails readWalletDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void getId() {
        readWalletDetails = new ReadWalletDetails(LoginActivity.this);
        password = findViewById(R.id.password_ed);
    }

    @Override
    public void setListener() {
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!Validations.hasText(password))
                    password.setError(getResources().getString(R.string.error_password_empty));
                else if (password.getText().toString().equals(readWalletDetails.getPassword())) {
                    SaveWalletDetails saveWalletDetails = new SaveWalletDetails(LoginActivity.this);
                    saveWalletDetails.saveIsLogin(true);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.error_password_not_match), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
