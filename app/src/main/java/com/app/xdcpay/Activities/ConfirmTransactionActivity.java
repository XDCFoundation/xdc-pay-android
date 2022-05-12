package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.XDCAndroid.XDC20Client;
import com.XDCAndroid.XDC721Client;
import com.XDCJava.XDCpayClient;
import com.app.xdcpay.Activities.Networks.AddNetworkActivity;
import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.Entity.TransactionsEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;

import java.lang.ref.WeakReference;

public class ConfirmTransactionActivity extends BaseActivity {
    private TextViewMedium btnConfirm;
    private ImageView iv_back;
    private String sender_str, receiver_str, amount_str, gas_limit_str, gas_price_str;
    private TextView sender, receiver, amount, gas_limit, gas_price, transaction_fee, total;
    private ReadWalletDetails readWalletDetails;
    private NetworkDataBase networkDataBase;
    private TransactionsEntity transactionsEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_transaction);
    }

    @Override
    public void getId() {
        readWalletDetails = new ReadWalletDetails(ConfirmTransactionActivity.this);
        btnConfirm = findViewById(R.id.btnConfirm);
        iv_back = findViewById(R.id.iv_back);
        setData();
    }

    @Override
    public void setListener() {
        btnConfirm.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null) {
            sender_str = intent.getStringExtra(Constants.SENDER);
            receiver_str = intent.getStringExtra(Constants.RECEIVER);
            amount_str = intent.getStringExtra(Constants.AMOUNT);
            gas_limit_str = intent.getStringExtra(Constants.GAS_LIMIT);
            gas_limit_str = intent.getStringExtra(Constants.GAS_PRICE);
            sender.setText(sender_str + R.string.txt_xdc);
            receiver.setText(receiver_str + R.string.txt_xdc);
            gas_limit.setText(gas_limit_str + R.string.txt_xdc);
            gas_price.setText(gas_price_str + R.string.txt_xdc);
            amount.setText(amount_str + R.string.txt_xdc);
        }

        networkDataBase = NetworkDataBase.getInstance(ConfirmTransactionActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                String hash = XDC20Client.getInstance().TransferXdc(readWalletDetails.getPrivateKey(), sender_str, receiver_str, Double.parseDouble(amount_str), Double.parseDouble(gas_price_str), Double.parseDouble(gas_limit_str));
                transactionsEntity = new TransactionsEntity(sender_str, receiver_str, amount_str, gas_limit_str, gas_price_str, hash);
                new InsertTask(ConfirmTransactionActivity.this, transactionsEntity).execute();
                Intent i = new Intent(ConfirmTransactionActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConfirmTransactionActivity.this, SendActivity.class);
        startActivity(i);
        finish();
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<ConfirmTransactionActivity> activityReference;
        private TransactionsEntity transactionsEntity;

        public InsertTask(ConfirmTransactionActivity confirmTransactionActivity, TransactionsEntity transactionsEntity) {
            activityReference = new WeakReference<>(confirmTransactionActivity);
            this.transactionsEntity = transactionsEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getTransactionsDao().insertTransaction(transactionsEntity);
            startActivity(new Intent(ConfirmTransactionActivity.this, NetworksActivity.class));
            return null;
        }
    }
}