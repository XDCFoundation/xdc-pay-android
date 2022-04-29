package com.app.xdcpay.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Activities.Networks.NetworksActivity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.List;

public class NetworkListAdapter extends RecyclerView.Adapter<NetworkListAdapter.NetworkViewHolder> {
    private Context context;
    private List<NetworkEntity> networkLists = new ArrayList<>();

    public NetworkListAdapter(Context context, List<NetworkEntity> networkLists) {
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
        NetworkEntity model = networkLists.get(position);
        ;
        holder.tv_Networks.setText(model.getNetworkName());
        Log.d("RoomList: ", "" + model.getNetworkName());
//        holder.courseDescTV.setText(model.getCourseDescription());
//        holder.courseDurationTV.setText(model.getCourseDuration());
    }


    @Override
    public int getItemCount() {
        return networkLists.size();
    }

    public static class NetworkViewHolder extends RecyclerView.ViewHolder {
        TextViewMedium tv_Networks;

        public NetworkViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Networks = itemView.findViewById(R.id.tv_Networks);
        }
    }
}