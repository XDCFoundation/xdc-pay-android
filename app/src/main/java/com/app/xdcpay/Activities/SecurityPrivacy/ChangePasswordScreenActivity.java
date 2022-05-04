package com.app.xdcpay.Activities.SecurityPrivacy;

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
import android.widget.TextView;
import android.widget.Toast;

import com.XDCJava.XDCpayClient;
import com.XDCJava.Model.WalletData;
import com.XDCJava.callback.CreateAccountCallback;
import com.app.xdcpay.Activities.ImportWalletActivity;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.gson.Gson;

import java.io.File;

public class ChangePasswordScreenActivity extends BaseActivity {

    private EditText password, confirm_password;
    private CheckBox terms_cb;
    private TextView title, show_hide;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
    }

    @Override
    public void getId() {
        password = findViewById(R.id.password_et);
        confirm_password = findViewById(R.id.confirm_password_et);
        terms_cb = findViewById(R.id.terms_cb);
        title = findViewById(R.id.title);
        show_hide = findViewById(R.id.show_hide);
        progressBar = findViewById(R.id.password_strength_progress);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.reset_password).setOnClickListener(this);
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

        setData();
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.change_password));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.reset_password:
                if (isValid()) {
                    SaveWalletDetails saveWalletDetails = new SaveWalletDetails(ChangePasswordScreenActivity.this);
                    saveWalletDetails.savePassword(password.getText().toString());
                    Toast.makeText(ChangePasswordScreenActivity.this, getResources().getString(R.string.password_updated), Toast.LENGTH_SHORT).show();
                    finish();
                }

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
        if (!Validations.hasText(password))
            password.setError(getResources().getString(R.string.error_password_empty));
        else if (!Validations.hasText(confirm_password))
            confirm_password.setError(getResources().getString(R.string.error_password_empty));
        else if (password.getText().toString().length() < 8)
            Toast.makeText(ChangePasswordScreenActivity.this, getResources().getString(R.string.password_length_msg), Toast.LENGTH_SHORT).show();
        else if (!Validations.isPasswordValid(password.getText().toString()))
            Toast.makeText(ChangePasswordScreenActivity.this, getResources().getString(R.string.password_strength_msg), Toast.LENGTH_SHORT).show();
        else if (!password.getText().toString().equals(confirm_password.getText().toString()))
            confirm_password.setError(getResources().getString(R.string.error_password_not_match));
        else if (!terms_cb.isChecked())
            Toast.makeText(ChangePasswordScreenActivity.this, getResources().getString(R.string.error_check_terms), Toast.LENGTH_SHORT).show();
        else return true;

        return false;
    }
}