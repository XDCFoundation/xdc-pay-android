package com.app.xdcpay.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Adapters.TransactionsAdapter;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class TransactionActivity extends BaseActivity {
    public static String ACTIVITY_NAME = "ACTIVITY_NAME";
    private TextViewMedium tvTitle;
    ImageView ivAdd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
    }

    @Override
    public void getId() {
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.transactions));
        ivAdd = findViewById(R.id.ivAdd);
        ivAdd.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.transactions_rv);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.ivBack).setOnClickListener(this);
    }

    @Override
    public void setData() {
        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(TransactionActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
        recyclerView.setAdapter(transactionsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }





    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}