package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TokensAdapter extends RecyclerView.Adapter<TokensAdapter.TokensViewHolder> {
    private Context context;

    public TokensAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TokensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_tokens_list_row, parent, false);
        return new TokensViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TokensViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class TokensViewHolder extends RecyclerView.ViewHolder {
        public TokensViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
