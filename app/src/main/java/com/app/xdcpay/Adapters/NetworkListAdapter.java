package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Model.NetworkList;
import com.app.xdcpay.R;

import java.util.ArrayList;
import java.util.List;

public class NetworkListAdapter extends RecyclerView.Adapter<NetworkListAdapter.NetworkViewHolder> {
    private Context context;
    private List<NetworkList> networkLists = new ArrayList<>();

    public NetworkListAdapter(List<NetworkList> networkLists, Context context) {
        this.context = context;
        this.networkLists = networkLists;
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

    }

    @Override
    public int getItemCount() {
        return networkLists.size();
    }

    public static class NetworkViewHolder extends RecyclerView.ViewHolder {
        public NetworkViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}