package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.XDCJava.FleekClient;
import com.XDCJava.Model.WalletData;
import com.XDCJava.callback.CreateAccountCallback;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;
import com.google.gson.Gson;

import java.io.File;

public class CreateWalletActivity extends BaseActivity {
    private EditText password, confirm_password;
    private CheckBox terms_cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
    }

    @Override
    public void getId() {
        password = findViewById(R.id.password_ed);
        confirm_password = findViewById(R.id.confirm_password_ed);
        terms_cb = findViewById(R.id.terms_cb);
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create_password).setOnClickListener(this);


    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.create_password:
                if (isValid()) {
                    File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES +
                            File.separator + "web3j");
                    path.mkdir();
                    FleekClient.getInstance().generateWallet(path, password.getText().toString(), new CreateAccountCallback() {
                        @Override
                        public void success(WalletData walletData) {
                            Intent intent = new Intent(CreateWalletActivity.this, HomeActivity.class);
                            intent.putExtra(Constants.WALLET_DATA, new Gson().toJson(walletData));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void failure(Throwable t) {


                        }

                        @Override
                        public void failure(String message) {

                        }
                    });

                }
                break;
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(password))
            password.setError(getResources().getString(R.string.error_password_empty));
        else if (!Validations.hasText(confirm_password))
            confirm_password.setError(getResources().getString(R.string.error_password_empty));
        else if (!password.equals(confirm_password))
            confirm_password.setError(getResources().getString(R.string.error_password_not_match));
        else if (!terms_cb.isChecked())
            Toast.makeText(CreateWalletActivity.this, getResources().getString(R.string.error_check_terms), Toast.LENGTH_SHORT).show();
        else return true;

        return false;
    }

    private void updatePasswordStrengthView(String password) {

    }

}
