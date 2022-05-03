package com.app.xdcpay.Activities.Accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.XDCJava.Model.WalletData;
import com.XDCJava.callback.CreateAccountCallback;
import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.Activities.ImportWalletActivity;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.gson.Gson;

import java.io.File;

public class ImportAccountActivity extends BaseActivity {
    private AppCompatSpinner spnType;
    private EditText etPrivateKey;
    private TextViewMedium title, btn_Import;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_account);

    }

    @Override
    public void getId() {
        spnType = findViewById(R.id.spnType);
        etPrivateKey = findViewById(R.id.etPrivateKey);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        btn_Import = findViewById(R.id.btn_Import);
        setData();
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        btn_Import.setOnClickListener(this);

        etPrivateKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    btn_Import.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_corner_5dp_light_green_bg));
                }
                else {
                    btn_Import.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_corner_5dp_green_bg));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.import_account));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_Import:
                if (isValid())
                    checkYourKey();

        }
    }

    private void checkYourKey() {
        try {
            File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + "web3j");
            path.mkdir();

//            FleekClient.getInstance().importWallet(etPrivateKey.getText().toString(), etPrivateKey.getText().toString(), path, new CreateAccountCallback() {
//                @Override
//                public void success(WalletData walletData) {
//                    Gson gson = new Gson();
//                    String json = gson.toJson(walletData);
////                            SharedPreferenceHelper.setSharedPreferenceString(ImportWalletActivity.this, "userwallet", json);
//
//                    if (walletData != null) {
//                        SaveWalletDetails saveWalletDetails = new SaveWalletDetails(ImportAccountActivity.this);
//                        saveWalletDetails.savePrivateKey(walletData.getPrivateKey());
//                        saveWalletDetails.savePublicKey(walletData.getPublickeyKey());
//                        saveWalletDetails.saveAccountAddress(walletData.getAccountAddress());
//                        saveWalletDetails.saveSeedPhrase(walletData.getSeedPhrase());
//                        saveWalletDetails.savePassword(walletData.getPassword());
//                        saveWalletDetails.saveIsLogin(true);
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(ImportAccountActivity.this, HomeActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        }, 500);
//                    }
//                }
//
//                @Override
//                public void failure(Throwable t) {
//                    //txt_info.setText(t.getMessage());
//                }
//
//                @Override
//                public void failure(String message) {
//                    //txt_info.setText(message);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(etPrivateKey))
            etPrivateKey.setError(getResources().getString(R.string.private_key));

        else return true;

        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}