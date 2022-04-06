package com.app.xdcpay.Activities;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

public class ContactDetailsActivity extends BaseActivity {
    private TextViewMedium ivEdit;
    private TextViewMedium tvTitle,tvNoTransaction;
    private ImageView ivBack;
    private RecyclerView id_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

    }

    @Override
    public void getId() {
        ivEdit = findViewById(R.id.ivEdit);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        tvNoTransaction = findViewById(R.id.tvNoTransaction);
        id_recycler = findViewById(R.id.id_recycler);
        tvTitle.setText(getText(R.string.contacts));
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);

    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEdit:
                Intent intentContact = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                startActivity(intentContact);
                finish();
                break;
            case R.id.ivBack:
                onBackPressed();
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