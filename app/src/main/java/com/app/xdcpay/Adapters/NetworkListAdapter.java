package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.Interface.NetworkListInterface;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.List;

public class NetworkListAdapter extends RecyclerView.Adapter<NetworkListAdapter.NetworkViewHolder> {
    private Context context;
    private List<NetworkEntity> networkLists = new ArrayList<>();
    NetworkListInterface networkCallback;

    public NetworkListAdapter(Context context, List<NetworkEntity> networkLists, NetworkListInterface networkCallback) {
        this.context = context;
        this.networkLists = networkLists;
        this.networkCallback = networkCallback;
    }

    @NonNull
    @Override
    public NetworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_network_row, parent, false);
        return new NetworkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkViewHolder holder, int position) {
        NetworkEntity model = networkLists.get(position);
        holder.tv_Networks.setText(model.getNetworkName());

        holder.id_linear_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                networkCallback.networkListOnClickListener(position, networkLists);
              /*  Intent intent = new Intent(context, NetworkDetailsActivity.class);
                intent.putExtra(NETWORK_NAME, model.getNetworkName());
                intent.putExtra(NETWORK_RPC_URL, model.getRpcUrl());
                intent.putExtra(CHAIN_ID, model.getChainId());
                intent.putExtra(CURRENCY_SYMBOL, model.getCurrencySymbol());
                intent.putExtra(BLOCK_EXPLORE_URL, model.getBlockExplorerUrl());
                context.startActivity(intent);*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return networkLists.size();
    }

    public static class NetworkViewHolder extends RecyclerView.ViewHolder {
        private TextViewMedium tv_Networks;
        private LinearLayout id_linear_network;

        public NetworkViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Networks = itemView.findViewById(R.id.tv_Networks);
            id_linear_network = itemView.findViewById(R.id.id_linear_network);
        }
    }
}