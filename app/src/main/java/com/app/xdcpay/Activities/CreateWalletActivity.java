package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.XDCJava.XDCpayClient;
import com.XDCJava.Model.WalletData;
import com.XDCJava.callback.CreateAccountCallback;
import com.app.xdcpay.Activities.CreateWallet.WalletSeedPhraseActivity;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.gson.Gson;

import java.io.File;

public class CreateWalletActivity extends BaseActivity {
    private EditText password, confirm_password;
    private CheckBox terms_cb;
    private ProgressBar progressBar;
    private TextViewMedium show_hide, title;

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
        progressBar = findViewById(R.id.password_strength_progress);
        show_hide = findViewById(R.id.show_hide);
        title = findViewById(R.id.title);
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.create_password).setOnClickListener(this);
        title.setText(R.string.create_new_wallet);
        show_hide.setOnClickListener(this);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePasswordStrengthView(password, progressBar);
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
            case R.id.back:
                finish();
                break;

            case R.id.create_password:
                if (isValid()) {
                    File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES +
                            File.separator + "web3j");
                    path.mkdir();
                    XDCpayClient.getInstance().generateWallet(path, password.getText().toString(), new CreateAccountCallback() {
                        @Override
                        public void success(WalletData walletData) {
                            if (walletData != null) {
                                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(CreateWalletActivity.this);
                                saveWalletDetails.savePrivateKey(walletData.getPrivateKey());
                                saveWalletDetails.savePublicKey(walletData.getPublickeyKey());
                                saveWalletDetails.saveAccountAddress(walletData.getAccountAddress());
                                saveWalletDetails.saveSeedPhrase(walletData.getSeedPhrase());
                                saveWalletDetails.savePassword(walletData.getPassword());
//                                saveWalletDetails.saveIsLogin(true);

                                Intent intent = new Intent(CreateWalletActivity.this, WalletSeedPhraseActivity.class);
                                intent.putExtra(Constants.WALLET_DATA, new Gson().toJson(walletData));
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateWalletActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
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

            case R.id.show_hide:
                if (password.getTransformationMethod() == null) {
                    show_hide.setText(R.string.show);
                    password.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    password.setTransformationMethod(null);
                    show_hide.setText(R.string.hide);
                }
                break;
        }
    }

    private boolean isValid() {
        String strPassword = password.getText().toString().trim();
        String strConfirmPassword = confirm_password.getText().toString().trim();
        if (!Validations.hasText(password))
            password.setError(getResources().getString(R.string.error_password_empty));
        else if (!Validations.hasText(confirm_password))
            confirm_password.setError(getResources().getString(R.string.error_password_empty));
        else if (password.getText().toString().length() < 8)
            Toast.makeText(CreateWalletActivity.this, getResources().getString(R.string.password_length_msg), Toast.LENGTH_SHORT).show();
        else if (!Validations.isPasswordValid(password.getText().toString()))
            Toast.makeText(CreateWalletActivity.this, getResources().getString(R.string.password_strength_msg), Toast.LENGTH_SHORT).show();
        else if (!strPassword.equals(strConfirmPassword))
            confirm_password.setError(getResources().getString(R.string.error_password_not_match));
        else if (!terms_cb.isChecked())
            Toast.makeText(CreateWalletActivity.this, getResources().getString(R.string.error_check_terms), Toast.LENGTH_SHORT).show();
        else return true;

        return false;
    }

    private void updatePasswordStrengthView(String password) {

    }

}