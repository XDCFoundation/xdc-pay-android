package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.xdcpay.DataBase.Entity.TransactionsEntity;
import com.app.xdcpay.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    private Context context;
    private List<TransactionsEntity> entity;

    public TransactionsAdapter(Context context, List<TransactionsEntity> entity) {
        this.context = context;
        this.entity = entity;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_transactions_list_row, parent, false);
        return new TransactionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        holder.transaction_id.setText(entity.get(position).getTransaction_hash());
        holder.amount.setText(entity.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return entity.size();
    }

    public class TransactionsViewHolder extends RecyclerView.ViewHolder {
        private TextView transaction_id, date_time, amount;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            date_time = itemView.findViewById(R.id.date_time);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
