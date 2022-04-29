package com.app.xdcpay.Activities.Networks;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.Adapters.NetworkListAdapter;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Model.NetworkViewModal;
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
    private final List<NetworkEntity> networkLists = new ArrayList<>();
    private NetworkEntity networkModelList;
    private String[] networkTitle = {"XDC ApothemNetwork", "Localhost 8545", "Pegasus Test Network (v1.1)",
            "Lio Test Network (v1.1)", "Orion Test Network (v1.1)", "XDC Devnet", "Localhost 8545", "Custom RPC"};
    private NetworkViewModal networkViewModal;

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
        setData();
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivAddNetworks.setOnClickListener(this);
        linear_network.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkListAdapter = new NetworkListAdapter(getApplicationContext(),
                NetworkDataBase.getInstance(getApplicationContext()).getNetworkDao().getNetworkList());

        recycler_Networks.setLayoutManager(new LinearLayoutManager(this));
        recycler_Networks.setHasFixedSize(true);
        recycler_Networks.setAdapter(networkListAdapter);

//        networkViewModal = ViewModelProviders.of(this).get(NetworkViewModal.class);
//        networkViewModal.getAllCourses().observe(this, new Observer<List<NetworkEntity>>() {
//            @Override
//            public void onChanged(List<NetworkEntity> networkEntities) {
//
//                // when the data is changed in our models we are adding that list to our adapter class.
////                networkListAdapter.sub
//            }
//        });
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
