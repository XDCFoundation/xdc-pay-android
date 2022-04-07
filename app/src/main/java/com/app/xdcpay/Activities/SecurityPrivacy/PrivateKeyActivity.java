package com.app.xdcpay.Activities.SecurityPrivacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

public class PrivateKeyActivity extends BaseActivity {
    EditText et_password;
    TextViewMedium revealBtn, title;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_key);
    }

    @Override
    public void getId() {
        et_password = findViewById(R.id.et_password);
        revealBtn = findViewById(R.id.revealBtn);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {
        revealBtn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.show_private_key));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.revealBtn:
                if (isValid()) {
                    Intent i = new Intent(PrivateKeyActivity.this, ShowPrivateKeyActivity.class);
                    startActivity(i);
                    finish();
                    break;
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(et_password))
            et_password.setError(getResources().getString(R.string.error_password_empty));
        else
            return true;

        return false;
    }
}