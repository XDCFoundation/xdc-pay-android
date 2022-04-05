package com.app.xdcpay.Activities;

import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

public class AddContactActivity extends BaseActivity {
    private AppCompatButton btn_addContact, btn_contactCancel;
    private TextViewMedium tvTitle;
    private EditText etWalletAddress, etUserName;
    private ImageView back;
    private TextViewMedium title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    @Override
    public void getId() {
        btn_addContact = findViewById(R.id.btn_addContact);
        btn_contactCancel = findViewById(R.id.btn_contactCancel);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        etWalletAddress = findViewById(R.id.etWalletAddress);
        etUserName = findViewById(R.id.etUserName);
        title.setText(getString(R.string.add_networks));
    }

    @Override
    public void setListener() {
        btn_addContact.setOnClickListener(this);
        btn_contactCancel.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_addContact:
                if (isValid()) {

                }
                break;
            case R.id.btn_contactCancel:
                Intent intent = new Intent(AddContactActivity.this, ContactsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddContactActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
//        super.onBackPressed();
    }

    private boolean isValid() {
        if (!Validations.hasText(etWalletAddress))
            etWalletAddress.setError(getResources().getString(R.string.error_empty));
        else if (!Validations.hasText(etUserName))
            etUserName.setError(getResources().getString(R.string.error_password_empty));

        else return true;

        return false;
    }
}