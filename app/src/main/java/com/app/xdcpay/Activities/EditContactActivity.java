package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;
import static com.app.xdcpay.Activities.ScannerActivity.ADDRESS;
import static com.app.xdcpay.Utils.Constants.CONTACT_ID;
import static com.app.xdcpay.Utils.Constants.CONTACT_NAME;
import static com.app.xdcpay.Utils.Constants.CONTACT_WALLET;

import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.Activities.Contacts.ContactsActivity;
import com.app.xdcpay.Activities.Networks.NetworkDetailsActivity;
import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;

public class EditContactActivity extends BaseActivity {
    private AppCompatButton btn_updateContact, btn_contactCancel;
    private TextViewMedium tvTitle;
    private EditText etWalletAddress, etUserName;
    private ImageView back;
    private TextViewMedium title, ivEdit;
    private ImageView iv_barcodeScanner;
    int contactId;
    String strAddress;
    NetworkDataBase networkDataBase;
    String contactWalletAddress, contactName, contactNameSubstring;

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
        iv_barcodeScanner = findViewById(R.id.iv_barcodeScanner);
        etWalletAddress = findViewById(R.id.etWalletAddress);
        etUserName = findViewById(R.id.etUserName);
        title.setText(getString(R.string.edit_contact));
        ivEdit.setText(getString(R.string.txt_delete));
        setData();
    }

    @Override
    public void setListener() {
        btn_updateContact.setOnClickListener(this);
        btn_contactCancel.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        iv_barcodeScanner.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkDataBase = NetworkDataBase.getInstance(EditContactActivity.this);
        Intent i = getIntent();
        if (i != null) {
            contactWalletAddress = i.getStringExtra(CONTACT_WALLET);
            contactName = i.getStringExtra(CONTACT_NAME);
            contactId = i.getIntExtra(CONTACT_ID, 0);
            etUserName.setText(contactName);
            etWalletAddress.setText(contactWalletAddress);
            strAddress = i.getStringExtra(ADDRESS);
            if (strAddress != null) {
                etWalletAddress.setText(strAddress);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.ivEdit:
                deleteAccount();
                break;

            case R.id.iv_barcodeScanner:
                Intent intent = new Intent(EditContactActivity.this, ScannerActivity.class);
                intent.putExtra(ACTIVITY_NAME, "EditContactActivity");
                startActivity(intent);
                break;
            case R.id.btn_updateContact:
                if (isValid()) {
                    new UpdateTask(EditContactActivity.this).execute();
                }
                break;
            case R.id.btn_contactCancel:
                Intent intentCancel = new Intent(EditContactActivity.this, ContactsActivity.class);
                startActivity(intentCancel);
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
            etUserName.setError(getResources().getString(R.string.error_empty));

        else return true;

        return false;
    }

    private void deleteAccount() {
        new DeleteTask(EditContactActivity.this, contactId).execute();
    }

    private class DeleteTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<EditContactActivity> activityReference;
        private int contactId;

        public DeleteTask(EditContactActivity removeContact, int contactId) {
            activityReference = new WeakReference<>(removeContact);
            this.contactId = contactId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getContactDao().DeleteById(contactId);
            Intent i = new Intent(EditContactActivity.this, ContactsActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }

    private class UpdateTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<EditContactActivity> activityReference;


        public UpdateTask(EditContactActivity removeContact) {
            activityReference = new WeakReference<>(removeContact);

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            contactName = etUserName.getText().toString();
            contactNameSubstring = contactName.substring(0, 1);
            activityReference.get().networkDataBase.getContactDao().updateByID(contactName, contactNameSubstring,
                    etWalletAddress.getText().toString(), contactId);
            Intent i = new Intent(EditContactActivity.this, ContactsActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }
}