package com.app.xdcpay.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Adapters.TransactionsAdapter;
import com.app.xdcpay.DataBase.Entity.TransactionsEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.List;

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
        List<TransactionsEntity> entity = NetworkDataBase.getInstance(getApplicationContext()).getTransactionsDao().getTransactionsList();

        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(TransactionActivity.this, entity);
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