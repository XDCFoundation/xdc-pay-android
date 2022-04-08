package com.app.xdcpay.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;

public class SentActivity extends BaseActivity {
    private TextViewMedium copyWalletAddress, btnConfirm,title;
    ReadWalletDetails readWalletDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
    }

    @Override
    public void getId() {
        copyWalletAddress = findViewById(R.id.copyWalletAddress);
        btnConfirm = findViewById(R.id.btnConfirm);
        title = findViewById(R.id.title);
        readWalletDetails = new ReadWalletDetails(this);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        copyWalletAddress.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.sent));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyWalletAddress:
                if (!copyWalletAddress.getText().toString().isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Private Key", copyWalletAddress.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), getString(R.string.copy_to_clipboard), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_private_key), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnConfirm:
                Intent intent1 = new Intent(SentActivity.this, BrowserActivity.class);
                intent1.putExtra(Constants.TITLE, getResources().getString(R.string.view_on_observatory));
                intent1.putExtra(Constants.URL, Constants.OBSERVER_URL + readWalletDetails.getAccountAddress());
                startActivity(intent1);
                finish();
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SentActivity.this, ConfirmTransactionActivity.class);
        startActivity(i);
        finish();
    }
}