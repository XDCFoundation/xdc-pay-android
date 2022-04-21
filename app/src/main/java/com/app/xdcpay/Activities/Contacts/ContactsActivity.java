package com.app.xdcpay.Activities.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class ContactsActivity extends BaseActivity {
    private ImageView ivAdd;
    private TextViewMedium tvTitle;
    private ImageView back;
    private LinearLayout linear_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @Override
    public void getId() {
        ivAdd = findViewById(R.id.ivAdd);
        tvTitle = findViewById(R.id.tvTitle);
        back = findViewById(R.id.ivBack);
        linear_contact = findViewById(R.id.linear_contact);
        tvTitle.setText(getText(R.string.contacts));
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        linear_contact.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAdd:
                Intent intentContact = new Intent(ContactsActivity.this, AddContactActivity.class);
                startActivity(intentContact);
                finish();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.linear_contact:
                Intent intent = new Intent(ContactsActivity.this, ContactDetailsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(ContactsActivity.this, SettingsActivity.class);
//        startActivity(intent);
        super.onBackPressed();
        finish();
    }
}