package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Pref.SaveAutoLockTimerPref;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextView;

import java.util.ArrayList;

public class TimeLockerAdapter extends RecyclerView.Adapter<TimeLockerAdapter.SortByHolder> {

    private final ArrayList<Integer> list;
    private boolean isChecked;
    private Context context;

    public TimeLockerAdapter(Context context, ArrayList<Integer> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SortByHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SortByHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time_locker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SortByHolder holder, int position) {
//        holder.tvSortByOption.setText(list.get(position));
        if (list.get(position) > 60)
            holder.tvSortByOption.setText(context.getResources().getString(
                    R.string.minutes,
                    String.valueOf(list.get(position) / 60)));
        else
            holder.tvSortByOption.setText(context.getResources().getString(
                    R.string.seconds,
                    String.valueOf(list.get(position))));
        holder.tvSortByOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAutoLockTimerPref saveAutoLockTimerPref=new SaveAutoLockTimerPref(context);

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
