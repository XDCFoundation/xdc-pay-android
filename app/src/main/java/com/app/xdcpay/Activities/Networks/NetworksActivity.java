package com.app.xdcpay.Activities.Networks;

import static com.app.xdcpay.Activities.Networks.NetworkDetailsActivity.BLOCK_EXPLORE_URL;
import static com.app.xdcpay.Activities.Networks.NetworkDetailsActivity.CHAIN_ID;
import static com.app.xdcpay.Activities.Networks.NetworkDetailsActivity.CURRENCY_SYMBOL;
import static com.app.xdcpay.Activities.Networks.NetworkDetailsActivity.NETWORK_NAME;
import static com.app.xdcpay.Activities.Networks.NetworkDetailsActivity.NETWORK_RPC_URL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.Adapters.NetworkListAdapter;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Interface.NetworkListInterface;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.List;

public class NetworksActivity extends BaseActivity implements NetworkListInterface {
    private RecyclerView recycler_Networks;
    private TextViewMedium tvTitle;
    private ImageView ivBack, ivAddNetworks;
    private NetworkListAdapter networkListAdapter;
    private final List<NetworkEntity> networkLists = new ArrayList<>();
    private NetworkEntity networkModelList;
    private String[] networkTitle = {"XDC ApothemNetwork", "Localhost 8545", "Pegasus Test Network (v1.1)",
            "Lio Test Network (v1.1)", "Orion Test Network (v1.1)", "XDC Devnet", "Localhost 8545", "Custom RPC"};

    private LinearLayout linear_network, linear_network_Apothem, linear_network_localHost;

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
        linear_network_Apothem = findViewById(R.id.linear_network_Apothem);
        linear_network_localHost = findViewById(R.id.linear_network_localHost);
        tvTitle.setText(getString(R.string.networks));
        setData();
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivAddNetworks.setOnClickListener(this);
        linear_network.setOnClickListener(this);
        linear_network_Apothem.setOnClickListener(this);
        linear_network_localHost.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkListAdapter = new NetworkListAdapter(getApplicationContext(),
                NetworkDataBase.getInstance(getApplicationContext()).getNetworkDao().getNetworkList(),this);

        recycler_Networks.setLayoutManager(new LinearLayoutManager(this));
        recycler_Networks.setHasFixedSize(true);
        recycler_Networks.setAdapter(networkListAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
//                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(MainActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycler_Networks);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_network:
                Intent intent = new Intent(NetworksActivity.this, NetworkDetailsActivity.class);
                intent.putExtra(NETWORK_NAME, Constants.MAIN_NET_NAME);
                intent.putExtra(NETWORK_RPC_URL, Constants.MAIN_NET_RPC_URL);
                intent.putExtra(CHAIN_ID, Constants.MAIN_NET_ID);
                intent.putExtra(CURRENCY_SYMBOL, Constants.MAIN_NET_SYMBOL);
                intent.putExtra(BLOCK_EXPLORE_URL, Constants.MAIN_NET_URL);
                startActivity(intent);
                finish();
                break;

            case R.id.linear_network_Apothem:
                Intent apothemIntent = new Intent(NetworksActivity.this, NetworkDetailsActivity.class);
                apothemIntent.putExtra(NETWORK_NAME, Constants.APOTHEM_NAME);
                apothemIntent.putExtra(NETWORK_RPC_URL, Constants.APOTHEM_RPC_URL);
                apothemIntent.putExtra(CHAIN_ID, Constants.APOTHEM_ID);
                apothemIntent.putExtra(CURRENCY_SYMBOL, Constants.APOTHEM_SYMBOL);
                apothemIntent.putExtra(BLOCK_EXPLORE_URL, Constants.APOTHEM_URL);
                startActivity(apothemIntent);
                finish();
                break;
            case R.id.linear_network_localHost:
                Intent localHostIntent = new Intent(NetworksActivity.this, NetworkDetailsActivity.class);
                localHostIntent.putExtra(NETWORK_NAME, Constants.LOCALHOST_8545_NAME);
                localHostIntent.putExtra(NETWORK_RPC_URL, Constants.LOCALHOST_RPC_URL);
                localHostIntent.putExtra(CHAIN_ID, Constants.LOCALHOST_ID);
                localHostIntent.putExtra(CURRENCY_SYMBOL, Constants.LOCALHOST_SYMBOL);
                localHostIntent.putExtra(BLOCK_EXPLORE_URL, Constants.LOCALHOST_URL);
                startActivity(localHostIntent);
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

    @Override
    public void networkListOnClickListener(int pos, List<NetworkEntity> networkLists) {
        Intent intent = new Intent(NetworksActivity.this, NetworkDetailsActivity.class);
        intent.putExtra(NETWORK_NAME, networkLists.get(pos).getNetworkName());
        intent.putExtra(NETWORK_RPC_URL, networkLists.get(pos).getRpcUrl());
        intent.putExtra(CHAIN_ID, networkLists.get(pos).getChainId());
        intent.putExtra(CURRENCY_SYMBOL, networkLists.get(pos).getCurrencySymbol());
        intent.putExtra(BLOCK_EXPLORE_URL, networkLists.get(pos).getBlockExplorerUrl());
        startActivity(intent);
        finish();
    }
}
