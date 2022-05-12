package com.app.xdcpay.Activities.Contacts;

import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.Activities.Networks.AddNetworkActivity;
import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.DataBase.Entity.ContactEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;

public class AddContactActivity extends BaseActivity {
    private AppCompatButton btn_addContact, btn_contactCancel;
    private TextViewMedium tvTitle;
    private EditText etWalletAddress, etUserName;
    private ImageView back;
    private TextViewMedium title;
    private ContactEntity contactEntity;
    NetworkDataBase myDataBase;
    String contactName, contactNameSubstring;

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
        setData();
    }

    @Override
    public void setData() {
        myDataBase = NetworkDataBase.getInstance(AddContactActivity.this);
        title.setText(getString(R.string.add_contact));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_addContact:
                if (isValid()) {
                    contactName = etUserName.getText().toString();
                    contactNameSubstring = contactName.substring(0, 1);
                    contactEntity = new ContactEntity(etUserName.getText().toString(),
                            etWalletAddress.getText().toString(), contactNameSubstring);
                    new InsertTask(AddContactActivity.this, contactEntity).execute();
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
            etUserName.setError(getResources().getString(R.string.error_empty));

        else return true;

        return false;
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<AddContactActivity> activityReference;
        private ContactEntity contactEntity;

        public InsertTask(AddContactActivity addContactActivity, ContactEntity contactEntity) {
            activityReference = new WeakReference<>(addContactActivity);
            this.contactEntity = contactEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().myDataBase.getContactDao().InsertContact(contactEntity);
            startActivity(new Intent(AddContactActivity.this, ContactsActivity.class));
            finish();
            return null;
        }
    }
}