package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;
import static com.app.xdcpay.Activities.ScannerActivity.ADDRESS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

public class SendActivity extends BaseActivity {
    private ImageView iv_barcode;
    private EditText etReceiverAddress, etSenderAddress, etAmount,etGasPrice,etNote;
    private String strAddress;
    private ReadWalletDetails readWalletDetails;
    private TextViewMedium btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
    }

    @Override
    public void getId() {
        btn_next = findViewById(R.id.btn_next);
        iv_barcode = findViewById(R.id.iv_barcode);
        etReceiverAddress = findViewById(R.id.etReceiverAddress);
        etSenderAddress = findViewById(R.id.etSenderAddress);
        etGasPrice = findViewById(R.id.etGasPrice);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        readWalletDetails = new ReadWalletDetails(SendActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        iv_barcode.setOnClickListener(this);
    }

    @Override
    public void setData() {
        Intent i = getIntent();
        if (i != null) {
            strAddress = i.getStringExtra(ADDRESS);
            etReceiverAddress.setText(strAddress);
        }
        etSenderAddress.setText(readWalletDetails.getAccountAddress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.iv_barcode:
                Intent intent = new Intent(SendActivity.this, ScannerActivity.class);
                intent.putExtra(ACTIVITY_NAME, "SendActivity");
                startActivity(intent);
                break;

            case R.id.btn_next:
                if (isValid()) {
                    Intent i = new Intent(SendActivity.this, ConfirmTransactionActivity.class);
                    startActivity(i);
                    finish();
                    break;
                }
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(etReceiverAddress))
            etReceiverAddress.setError(getResources().getString(R.string.recipient_address_required));
        else if (!Validations.hasText(etSenderAddress))
            etSenderAddress.setError(getResources().getString(R.string.sender_address_required));
        else if (!Validations.hasText(etGasPrice))
            etGasPrice.setError(getResources().getString(R.string.gas_price_required));
        else if (!Validations.hasText(etAmount))
            etAmount.setError(getResources().getString(R.string.amount_required));
        else
            return true;

        return false;
    }
}
