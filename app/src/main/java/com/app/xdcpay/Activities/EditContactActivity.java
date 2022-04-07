package com.app.xdcpay.Activities;

import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.Activities.Contacts.ContactsActivity;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

public class EditContactActivity extends BaseActivity {
    private AppCompatButton btn_updateContact, btn_contactCancel;
    private TextViewMedium tvTitle;
    private EditText etWalletAddress, etUserName;
    private ImageView back;
    private TextViewMedium title,ivEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
    }

    @Override
    public void getId() {
        btn_updateContact = findViewById(R.id.btn_updateContact);
        btn_contactCancel = findViewById(R.id.btn_contactCancel);
        back = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        title = findViewById(R.id.tvTitle);
        etWalletAddress = findViewById(R.id.etWalletAddress);
        etUserName = findViewById(R.id.etUserName);
        title.setText(getString(R.string.edit_contact));
        ivEdit.setText(getString(R.string.txt_delete));
    }

    @Override
    public void setListener() {
        btn_updateContact.setOnClickListener(this);
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
                Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditContactActivity.this, ContactsActivity.class);
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