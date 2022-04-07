package com.app.xdcpay.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextView;

import java.util.ArrayList;

public class TimeLockerAdapter extends RecyclerView.Adapter<TimeLockerAdapter.SortByHolder> {

    private final ArrayList<String> list;
    private boolean isChecked;

    public TimeLockerAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SortByHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SortByHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time_locker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SortByHolder holder, int position) {
        holder.tvSortByOption.setText(list.get(position));
        holder.tvSortByOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                holder.tvSortByOption.setChecked(!holder.tvSortByOption.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SortByHolder extends RecyclerView.ViewHolder {
        private TextView tvSortByOption;
        private AppCompatImageView iv_Checked;

        public SortByHolder(@NonNull View itemView) {
            super(itemView);
            tvSortByOption = itemView.findViewById(R.id.tvTimer);
            iv_Checked = itemView.findViewById(R.id.iv_Checked);
        }
    }
}
