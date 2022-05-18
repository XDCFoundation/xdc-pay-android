package com.app.xdcpay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialogImport = new BottomSheetDialog(context);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.layout_token_options_dialog);
                bottomSheetDialogImport.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class TokensViewHolder extends RecyclerView.ViewHolder {
        private ImageView options;

        public TokensViewHolder(@NonNull View itemView) {
            super(itemView);
            options = itemView.findViewById(R.id.options_iv);
        }
    }
}
