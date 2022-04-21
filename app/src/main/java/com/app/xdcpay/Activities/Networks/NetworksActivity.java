package com.app.xdcpay.Activities.Networks;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.Adapters.NetworkListAdapter;
import com.app.xdcpay.Model.NetworkList;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.List;

public class NetworksActivity extends BaseActivity {
    private RecyclerView recycler_Networks;
    private TextViewMedium tvTitle;
    private ImageView ivBack, ivAddNetworks;
    private NetworkListAdapter networkListAdapter;
    private final List<NetworkList> networkLists = new ArrayList<>();
    private NetworkList networkModelList;
    private String[] networkTitle = {"XDC ApothemNetwork", "Localhost 8545", "Pegasus Test Network (v1.1)", "Lio Test Network (v1.1)"
            , "Orion Test Network (v1.1)", "XDC Devnet", "Localhost 8545", "Custom RPC"};

    private LinearLayout linear_network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networks);
    }

    @Override
    public void getId() {
        recycler_Networks = findViewById(R.id.recycler_Networks);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        ivAddNetworks = findViewById(R.id.ivAdd);
        linear_network = findViewById(R.id.linear_network);
        tvTitle.setText(getString(R.string.networks));
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivAddNetworks.setOnClickListener(this);
        linear_network.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkListAdapter = new NetworkListAdapter(networkLists, this);
        recycler_Networks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_Networks.setAdapter(networkListAdapter);
        networkLists.clear();


        setNetworkList();
    }

    private void setNetworkList() {
        networkModelList = new NetworkList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_network:
                Intent intent = new Intent(NetworksActivity.this, NetworkDetailsActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.ivAdd:
                Intent intentNetwork = new Intent(NetworksActivity.this, AddNetworkActivity.class);
                startActivity(intentNetwork);
                finish();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NetworksActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
//        super.onBackPressed();
    }
}
