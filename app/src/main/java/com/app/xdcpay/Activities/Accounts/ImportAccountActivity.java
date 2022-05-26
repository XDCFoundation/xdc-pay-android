package com.app.xdcpay.Activities.Accounts;

import static com.app.xdcpay.Utils.Constants.ACCOUNT_IMPORTED;
import static com.app.xdcpay.Utils.Constants.ACCOUNT_NAME;
import static com.app.xdcpay.Utils.Constants.keyTypeList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.XDCJava.Model.WalletData;
import com.XDCJava.XDCpayClient;
import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.ref.WeakReference;
import java.util.List;

public class ImportAccountActivity extends BaseActivity {
    private AppCompatSpinner spnType;
    private EditText etPrivateKey;
    private com.app.xdcpay.Views.TextView tvDropDown;
    private TextViewMedium title, btn_Import;
    private ImageView back;
    private String str_accountName;
    AccountEntity accountEntity;
    public NetworkDataBase networkDataBase;
    List<AccountEntity> networkLists;
    private boolean isAccountExist = false;

    BottomSheetDialog bottomSheetDialogImport;
    KeyTypeAdapter keyTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_account);

    }

    @Override
    public void getId() {
        spnType = findViewById(R.id.spnType);
        tvDropDown = findViewById(R.id.tvDropDown);
        etPrivateKey = findViewById(R.id.etPrivateKey);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        btn_Import = findViewById(R.id.btn_Import);
        networkDataBase = NetworkDataBase.getInstance(ImportAccountActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        btn_Import.setOnClickListener(this);
        tvDropDown.setOnClickListener(this);

        etPrivateKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    btn_Import.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_5dp_light_green_bg));
                } else {
                    btn_Import.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_corner_5dp_green_bg));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setData() {
        networkLists = NetworkDataBase.getInstance(getApplicationContext()).getAccountDao().getAccountList();
        Intent i = getIntent();
        title.setText(getString(R.string.import_account));
        if (i != null)
            str_accountName = i.getStringExtra(ACCOUNT_NAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_Import:
                if (isValid())
                    checkYourKey(etPrivateKey.getText().toString());
                break;

            case R.id.tvDropDown:
                bottomSheetDialogImport = new BottomSheetDialog(ImportAccountActivity.this);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.fragment_timer_bottom_sheet);
                TextView tvHeader = (TextView) bottomSheetDialogImport.findViewById(R.id.tvHeader);
                RecyclerView rvTimeLocker = (RecyclerView) bottomSheetDialogImport.findViewById(R.id.rvTimeLocker);
                tvHeader.setText(getString(R.string.select_key_Type));

                keyTypeAdapter = new KeyTypeAdapter();

                rvTimeLocker.setLayoutManager(new LinearLayoutManager(this));
                rvTimeLocker.setHasFixedSize(true);
                rvTimeLocker.setAdapter(keyTypeAdapter);
                bottomSheetDialogImport.show();

        }
    }

    private void checkYourKey(String privateKey) {
        try {
            WalletData walletData = XDCpayClient.getInstance().getAccountAddFromPrivateKey(privateKey);

            if (walletData != null) {
                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(ImportAccountActivity.this);
                saveWalletDetails.savePrivateKey(walletData.getPrivateKey());
                saveWalletDetails.savePublicKey(walletData.getPublickeyKey());
                saveWalletDetails.saveAccountAddress(walletData.getAccountAddress());
                String add = walletData.getAccountAddress();
                if (add.startsWith("0x"))
                    add = add.replaceFirst("0x", "xdc");
                accountEntity = new AccountEntity(str_accountName, add, walletData.getPrivateKey(),
                        walletData.getPublickeyKey(), "", ACCOUNT_IMPORTED);
                new InsertTask(ImportAccountActivity.this, accountEntity).execute();

            } else {
                showtoast(getResources().getString(R.string.invalid_privatekey));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(etPrivateKey))
            etPrivateKey.setError(getResources().getString(R.string.private_key));
        else if (etPrivateKey.getText().length() < 64) {
            showtoast(getResources().getString(R.string.invalid_privatekey));
        } else if (networkLists.size() > 0) {
            for (int i = 0; i < networkLists.size(); i++) {
                if (networkLists.get(i).getAccountPrivateKey().equals(etPrivateKey.getText().toString()))
                    isAccountExist = true;
            }
            if (isAccountExist)
                showtoast(getResources().getString(R.string.account_exist));
            else return true;
        } else
            return true;

        return false;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ImportAccountActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<ImportAccountActivity> activityReference;
        private AccountEntity networkEntity;

        public InsertTask(ImportAccountActivity addNetworkActivity, AccountEntity networkEntity) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.networkEntity = networkEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getAccountDao().insertAccount(networkEntity);
            Intent i = new Intent(ImportAccountActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }

    public class KeyTypeAdapter extends RecyclerView.Adapter<KeyTypeAdapter.TimerViewHolder> {
        @NonNull
        @Override
        public KeyTypeAdapter.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new KeyTypeAdapter.TimerViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_time_locker, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull KeyTypeAdapter.TimerViewHolder holder, int position) {

            holder.keyType.setText(keyTypeList.get(position));
            if (tvDropDown.getText().toString().equals(keyTypeList.get(position)))
                holder.iv_Checked.setVisibility(View.VISIBLE);
            else
                holder.iv_Checked.setVisibility(View.GONE);

            holder.timer_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bottomSheetDialogImport != null)
                        bottomSheetDialogImport.dismiss();
                    tvDropDown.setText(holder.keyType.getText().toString());
                    onUserInteraction();
                    onResume();
                }
            });
        }

        @Override
        public int getItemCount() {
            return keyTypeList.size();
        }

        public class TimerViewHolder extends RecyclerView.ViewHolder {
            private com.app.xdcpay.Views.TextView keyType;
            private ImageView iv_Checked;
            private LinearLayout timer_ll;

            public TimerViewHolder(@NonNull View itemView) {
                super(itemView);
                keyType = itemView.findViewById(R.id.tvTimer);
                iv_Checked = itemView.findViewById(R.id.iv_Checked);
                timer_ll = itemView.findViewById(R.id.timer_ll);
            }
        }
    }

}