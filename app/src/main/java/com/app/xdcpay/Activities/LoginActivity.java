package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.xdcpay.Activities.CreateWallet.ConfirmSeedPhraseActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.Pref.SharedPreferenceHelper;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;

public class LoginActivity extends BaseActivity {
    private EditText password;
    private ReadWalletDetails readWalletDetails;

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
    }

    @Override
    public void setListener() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.create_wallet).setOnClickListener(this);
        findViewById(R.id.restore_from_seed).setOnClickListener(this);

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
                    SharedPreferenceHelper.setSharedPreferenceString(LoginActivity.this, Constants.ACCOUNT, "0");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.error_incorrect_password), Toast.LENGTH_SHORT).show();
                break;

            case R.id.create_wallet:

                    Intent intent = new Intent(LoginActivity.this, CreateWalletActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                break;

            case R.id.restore_from_seed:

                Intent intentsedd = new Intent(LoginActivity.this, ImportWalletActivity.class);
                intentsedd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentsedd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentsedd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentsedd);

                break;
        }
    }
}
