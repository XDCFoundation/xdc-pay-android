package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.Interface.ImportAccountCallback;
import com.app.xdcpay.Interface.NetworkListInterface;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.List;

public class ImportedAccountAdapter extends RecyclerView.Adapter<ImportedAccountAdapter.AccountViewHolder> {
    private Context context;
    private List<AccountEntity> networkLists = new ArrayList<>();
    ImportAccountCallback networkCallback;

    public ImportedAccountAdapter(Context context, List<AccountEntity> networkLists, ImportAccountCallback networkCallback) {
        this.context = context;
        this.networkLists = networkLists;
        this.networkCallback = networkCallback;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.import_acount_row, parent, false);
        return new AccountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        AccountEntity model = networkLists.get(position);
        holder.tvAccountName.setText(context.getString(R.string.account) + " " + (position + 1));
//
//        holder.id_linear_network.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                networkCallback.networkListOnClickListener(position, networkLists);

//            }
//        });
        if (model.getAccountName().equals(context.getString(R.string.imported_text))) {

        }
        holder.account_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkCallback.AccountDeleteOnClickListener(networkLists.get(position).accountPrivateKey);

            }
        });
    }

    @Override
    public int getItemCount() {
        return networkLists.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private TextViewMedium tvAccountName;
        private ImageView account_delete;
//        private LinearLayout id_linear_network;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            account_delete = itemView.findViewById(R.id.account_delete);
//            id_linear_network = itemView.findViewById(R.id.id_linear_network);
        }
    }
}
