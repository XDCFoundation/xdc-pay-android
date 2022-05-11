package com.app.xdcpay.Activities.Accounts;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.XDCJava.Model.WalletData;
import com.XDCJava.XDCpayClient;
import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;

public class ImportAccountActivity extends BaseActivity {
    private AppCompatSpinner spnType;
    private EditText etPrivateKey;
    private TextViewMedium title, btn_Import;
    private ImageView back;
    private String str_accountName;
    AccountEntity accountEntity;
    NetworkDataBase networkDataBase;

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
                    btn_Import.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_5dp_light_green_bg));
                } else {
                    btn_Import.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_5dp_green_bg));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setData() {
        Intent i = getIntent();
        title.setText(getString(R.string.import_account));
        networkDataBase = NetworkDataBase.getInstance(ImportAccountActivity.this);
        if (i != null)
            str_accountName = i.getStringExtra(getString(R.string.imported_text));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_Import:
                if (isValid())
                    checkYourKey(etPrivateKey.getText().toString());

        }
    }

    private void checkYourKey(String privateKey) {
        try {
            WalletData walletData = XDCpayClient.getInstance().getAccountAddFromPrivateKey(privateKey);

            if (walletData != null) {
                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(ImportAccountActivity.this);
                saveWalletDetails.savePrivateKey(walletData.getPrivateKey());
                saveWalletDetails.savePublicKey(walletData.getPublickeyKey());
                saveWalletDetails.saveAccountAddress(walletData.getAccountAddress());
                accountEntity = new AccountEntity(str_accountName, walletData.getAccountAddress(),
                        walletData.getPrivateKey(), walletData.getPublickeyKey());
                new InsertTask(ImportAccountActivity.this, accountEntity).execute();

            }
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
        Intent i = new Intent(ImportAccountActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

     class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<ImportAccountActivity> activityReference;
        private AccountEntity networkEntity;

        public InsertTask(ImportAccountActivity addNetworkActivity, AccountEntity networkEntity) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.networkEntity = networkEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getAccountDao().insertAccount(networkEntity);
            Intent i = new Intent(ImportAccountActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }
}