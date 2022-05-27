package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.xdcpay.Activities.CreateWallet.CreateWalletActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.Pref.SharedPreferenceHelper;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.TextView;

public class LoginActivity extends BaseActivity {
    private EditText password;
    private ReadWalletDetails readWalletDetails;
    private TextView tvPasswordErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        Button crashButton = new Button(this);
//        crashButton.setText("Test Crash");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));


    }

    @Override
    public void getId() {
        readWalletDetails = new ReadWalletDetails(LoginActivity.this);
        password = findViewById(R.id.password_ed);
        tvPasswordErr = findViewById(R.id.tvPasswordErr);
    }

    @Override
    public void setListener() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.create_wallet).setOnClickListener(this);
        findViewById(R.id.restore_from_seed).setOnClickListener(this);
        password.addTextChangedListener(new TextWatcher() {
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (!Validations.hasText(password)) {
                    tvPasswordErr.setVisibility(View.VISIBLE);
                    tvPasswordErr.setText(getResources().getString(R.string.error_password_empty));
                } else if (password.getText().toString().equals(readWalletDetails.getPassword())) {
                    SaveWalletDetails saveWalletDetails = new SaveWalletDetails(LoginActivity.this);
                    saveWalletDetails.saveIsLogin(true);
                    SharedPreferenceHelper.setSharedPreferenceString(LoginActivity.this, Constants.ACCOUNT, "0");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    tvPasswordErr.setVisibility(View.VISIBLE);
                    tvPasswordErr.setText(getResources().getString(R.string.error_incorrect_password));
                }
                break;

            case R.id.create_wallet:

                Intent intent = new Intent(LoginActivity.this, CreateWalletActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

            case R.id.restore_from_seed:

                Intent intentsedd = new Intent(LoginActivity.this, ImportWalletActivity.class);
//                intentsedd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intentsedd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intentsedd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentsedd);

                break;
        }
    }
}
