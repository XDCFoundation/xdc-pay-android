package com.app.xdcpay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class ContactsActivity extends BaseActivity {
    private ImageView ivAdd;
    private TextViewMedium tvTitle;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @Override
    public void getId() {
        ivAdd = findViewById(R.id.ivAdd);
        tvTitle = findViewById(R.id.tvTitle);
        back = findViewById(R.id.back);
        tvTitle.setText(getText(R.string.contacts));
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
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
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactsActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
//        super.onBackPressed();
    }
}