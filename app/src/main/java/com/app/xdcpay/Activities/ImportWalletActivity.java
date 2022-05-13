package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.XDCJava.Model.WalletData;
import com.XDCJava.XDCpayClient;
import com.XDCJava.callback.CreateAccountCallback;
import com.app.xdcpay.Activities.Accounts.ImportAccountActivity;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.Pref.SharedPreferenceHelper;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;
import com.google.gson.Gson;

import java.io.File;
import java.lang.ref.WeakReference;

public class ImportWalletActivity extends BaseActivity {
    private EditText seed_phrase, password, confirm_password;
    private TextView title, show;
    private ProgressBar progressBar;
    private CheckBox show_cb;
    AccountEntity accountEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);
        password = findViewById(R.id.password_ed);
        confirm_password = findViewById(R.id.confirm_password_ed);
        seed_phrase = findViewById(R.id.seed_phrase_ed);
        progressBar = findViewById(R.id.password_strength_progress);
        show_cb = findViewById(R.id.show_cb);
        show = findViewById(R.id.show);

        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.import_tv).setOnClickListener(this);
        show.setOnClickListener(this);
        if (seed_phrase.getText().toString().length() > 0)
            show_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b)
                        seed_phrase.setTransformationMethod(new PasswordTransformationMethod());
                    else
                        seed_phrase.setTransformationMethod(null);
                }
            });

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
        title.setText(getResources().getString(R.string.import_from_seed));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.show:
                if (show.getText().toString().equals(getResources().getString(R.string.show))) {
                    if (password.getText().toString().length() > 0)
                        password.setTransformationMethod(new PasswordTransformationMethod());
                    show.setText(getResources().getString(R.string.hide));
                } else {
                    password.setTransformationMethod(null);
                    show.setText(getResources().getString(R.string.show));
                }
                break;

            case R.id.import_tv:
                if (isValid())
                    try {
                        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + "web3j");
                        path.mkdir();

                        XDCpayClient.getInstance().importWallet(seed_phrase.getText().toString(), password.getText().toString(), path, new CreateAccountCallback() {
                            @Override
                            public void success(WalletData walletData) {
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


                                    accountEntity = new AccountEntity(getResources().getString(R.string.account_1), walletData.getAccountAddress(),
                                            walletData.getPrivateKey(), walletData.getPublickeyKey(), walletData.getSeedPhrase());
                                    new InsertTask(ImportWalletActivity.this, accountEntity).execute();
                                    SharedPreferenceHelper.setSharedPreferenceString(ImportWalletActivity.this, Constants.ACCOUNT, "1");



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


    class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<ImportWalletActivity> activityReference;
        private AccountEntity networkEntity;

        public InsertTask(ImportWalletActivity addNetworkActivity, AccountEntity networkEntity) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.networkEntity = networkEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getAccountDao().insertAccount(networkEntity);
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
            finish();
            return null;
        }
    }


    private boolean isValid() {
        if (!Validations.hasText(seed_phrase))
            seed_phrase.setError(getResources().getString(R.string.error_empty));
        else if (!Validations.hasText(password))
            password.setError(getResources().getString(R.string.error_password_empty));
        else if (!Validations.hasText(confirm_password))
            confirm_password.setError(getResources().getString(R.string.error_password_empty));
        else if (password.getText().toString().length() < 8)
            Toast.makeText(ImportWalletActivity.this, getResources().getString(R.string.password_length_msg), Toast.LENGTH_SHORT).show();
        else if (!Validations.isPasswordValid(password.getText().toString()))
            Toast.makeText(ImportWalletActivity.this, getResources().getString(R.string.password_strength_msg), Toast.LENGTH_SHORT).show();
        else if (!password.getText().toString().equals(confirm_password.getText().toString()))
            confirm_password.setError(getResources().getString(R.string.error_password_not_match));
        else return true;

        return false;
    }
}
