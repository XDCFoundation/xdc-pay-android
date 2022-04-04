package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.XDCJava.FleekClient;
import com.XDCJava.Model.WalletData;
import com.XDCJava.callback.CreateAccountCallback;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.google.gson.Gson;

import java.io.File;

public class ImportWalletActivity extends BaseActivity {
    private EditText seed_phrase, password, confirm_password;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);
        password = findViewById(R.id.password_ed);
        confirm_password = findViewById(R.id.confirm_password_ed);
        seed_phrase = findViewById(R.id.seed_phrase_ed);

        title.setText(getResources().getString(R.string.import_from_seed));
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.import_tv).setOnClickListener(this);
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

            case R.id.import_tv:
                if (isValid())
                    try {
                        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES +
                                File.separator + "web3j");
                        path.mkdir();

                        FleekClient.getInstance().importWallet(seed_phrase.getText().toString(), password.getText().toString(), path, new CreateAccountCallback() {
                            @Override
                            public void success(WalletData walletData) {

                                // txt_info.setText("Account address: " + walletData.getAccountAddress() + "\n" + "privateKey: " + walletData.getPrivateKey());
                                Gson gson = new Gson();
                                String json = gson.toJson(walletData);
//                            SharedPreferenceHelper.setSharedPreferenceString(ImportWalletActivity.this, "userwallet", json);

                                if (walletData != null) {
                                    SaveWalletDetails saveWalletDetails = new SaveWalletDetails(ImportWalletActivity.this);
                                    saveWalletDetails.savePrivateKey(walletData.getPrivateKey());
                                    saveWalletDetails.savePublicKey(walletData.getPublickeyKey());
                                    saveWalletDetails.saveAccountAddress(walletData.getAccountAddress());
                                    saveWalletDetails.saveSeedPhrase(walletData.getSeedPhrase());
                                    saveWalletDetails.savePassword(walletData.getPassword());
                                    saveWalletDetails.saveIsLogin(true);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(ImportWalletActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }, 500);
                                }
                            }

                            @Override
                            public void failure(Throwable t) {
                                //txt_info.setText(t.getMessage());
                            }

                            @Override
                            public void failure(String message) {
                                //txt_info.setText(message);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                break;
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(seed_phrase))
            seed_phrase.setError(getResources().getString(R.string.error_empty));
        else if (!Validations.hasText(password))
            password.setError(getResources().getString(R.string.error_password_empty));
        else if (!Validations.hasText(confirm_password))
            confirm_password.setError(getResources().getString(R.string.error_password_empty));
        else if (!password.getText().toString().equals(confirm_password.getText().toString()))
            confirm_password.setError(getResources().getString(R.string.error_password_not_match));
        else return true;

        return false;
    }
}
