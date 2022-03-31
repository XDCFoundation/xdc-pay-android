package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    private Context context;

    public TransactionsAdapter(Context context) {
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class TransactionsViewHolder extends RecyclerView.ViewHolder {

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
