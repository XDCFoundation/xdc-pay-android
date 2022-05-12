package com.app.xdcpay.Activities.Contacts;

import static com.app.xdcpay.Utils.Constants.CONTACT_ID;
import static com.app.xdcpay.Utils.Constants.CONTACT_NAME;
import static com.app.xdcpay.Utils.Constants.CONTACT_WALLET;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.Activities.EditContactActivity;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class ContactDetailsActivity extends BaseActivity {
    private TextViewMedium ivEdit, tvName, tvWalletAddress;
    private TextViewMedium tvTitle, tvNoTransaction;
    private ImageView ivBack;
    private RecyclerView id_recycler;
    int contactId;
    String contactWalletAddress, contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
    }

    @Override
    public void getId() {
        ivEdit = findViewById(R.id.ivEdit);
        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvName);
        ivBack = findViewById(R.id.ivBack);
        tvWalletAddress = findViewById(R.id.tvWalletAddress);
        tvNoTransaction = findViewById(R.id.tvNoTransaction);
        id_recycler = findViewById(R.id.id_recycler);
        tvTitle.setText(getText(R.string.contacts_details));
        setData();
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        tvWalletAddress.setOnClickListener(this);

    }

    @Override
    public void setData() {
        Intent i = getIntent();
        if (i != null) {
            contactWalletAddress = i.getStringExtra(CONTACT_WALLET);
            contactName = i.getStringExtra(CONTACT_NAME);
            contactId = i.getIntExtra(CONTACT_ID, 0);
            tvName.setText(contactName);
            tvWalletAddress.setText(contactWalletAddress);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEdit:
                Intent intentContact = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                intentContact.putExtra(CONTACT_NAME, contactName);
                intentContact.putExtra(CONTACT_WALLET, contactWalletAddress);
                intentContact.putExtra(CONTACT_ID, contactId);
                startActivity(intentContact);
                finish();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvWalletAddress:
                ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Address", tvWalletAddress.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), getString(R.string.copy_to_clipboard), Toast.LENGTH_LONG).show();
                break;

            case R.id.linear_contact:
                Intent intent = new Intent(ContactDetailsActivity.this, ContactDetailsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactDetailsActivity.this, ContactsActivity.class);
        startActivity(intent);
//        super.onBackPressed();
        finish();
    }

}