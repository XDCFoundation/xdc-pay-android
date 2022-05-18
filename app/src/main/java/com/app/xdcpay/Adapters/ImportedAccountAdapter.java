package com.app.xdcpay.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.Interface.ImportAccountCallback;
import com.app.xdcpay.Pref.SharedPreferenceHelper;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ImportedAccountAdapter extends RecyclerView.Adapter<ImportedAccountAdapter.AccountViewHolder> {
    private Context context;
    private List<AccountEntity> networkLists = new ArrayList<>();
    ImportAccountCallback networkCallback;
    BottomSheetDialog bottomSheetDialog;

    public ImportedAccountAdapter(Context context, List<AccountEntity> networkLists, ImportAccountCallback networkCallback, BottomSheetDialog bottomSheetDialogImport) {
        this.context = context;
        this.networkLists = networkLists;
        this.networkCallback = networkCallback;
        this.bottomSheetDialog = bottomSheetDialogImport;
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
        //  holder.tvAccountName.setText(context.getString(R.string.account) + " " + (position + 1));
        int accountId = model.getId();

        if (model.getAccountName().equals(context.getString(R.string.imported_text))) {
            holder.tvAccountName.setText(context.getString(R.string.account) + " " + accountId);
            holder.textImported.setVisibility(View.VISIBLE);
            holder.account_delete.setVisibility(View.VISIBLE);
        } else {
            holder.tvAccountName.setText(model.getAccountName());
            holder.textImported.setVisibility(View.GONE);
            holder.account_delete.setVisibility(View.GONE);
        }
        holder.account_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkCallback.AccountDeleteOnClickListener(model.getId());

            }
        });

        holder.tvAccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceHelper.setSharedPreferenceString(context.getApplicationContext(), Constants.ACCOUNT, position + "");
                HomeActivity.setAccount(context.getApplicationContext(), model.id, bottomSheetDialog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return networkLists.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private TextViewMedium tvAccountName, textImported;
        private ImageView account_delete;
        private LinearLayout linear_imported;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            account_delete = itemView.findViewById(R.id.account_delete);
            linear_imported = itemView.findViewById(R.id.linear_imported);
            textImported = itemView.findViewById(R.id.textImported);
//            id_linear_network = itemView.findViewById(R.id.id_linear_network);
        }
    }
}
