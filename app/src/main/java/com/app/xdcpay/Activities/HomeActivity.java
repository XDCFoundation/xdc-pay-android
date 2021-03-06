package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;
import static com.app.xdcpay.Utils.ApiConstants.API_KEY;
import static com.app.xdcpay.Utils.Constants.ACCOUNT_CREATED;
import static com.app.xdcpay.Utils.Constants.ACCOUNT_NAME;
import static com.app.xdcpay.Utils.Constants.APOTHEM_NAME;
import static com.app.xdcpay.Utils.Constants.LOCALHOST_8545_NAME;
import static com.app.xdcpay.Utils.Constants.MAIN_NET_NAME;
import static com.app.xdcpay.Utils.Constants.STRING_FORMAT;
import static com.app.xdcpay.Utils.Constants.TEXT_USD;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.XDCJava.Model.WalletData;
import com.XDCJava.XDCpayClient;
import com.XDCJava.callback.CreateAccountCallback;
import com.XDCJava.callback.EventCallback;
import com.app.xdcpay.Activities.Accounts.ImportAccountActivity;
import com.app.xdcpay.Adapters.ImportedAccountAdapter;
import com.app.xdcpay.Api.Presenter.CurrencyConversionPresenter;
import com.app.xdcpay.Api.View.IGetUSDValueOfXDCView;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Fragments.NFTFragment;
import com.app.xdcpay.Fragments.TokensFragment;
import com.app.xdcpay.Fragments.TransactionsFragment;
import com.app.xdcpay.Interface.AccountCallbackInterface;
import com.app.xdcpay.Interface.BottomSheetInterface;
import com.app.xdcpay.Interface.ImportAccountCallback;
import com.app.xdcpay.Fragments.NFTFragment;
import com.app.xdcpay.Pref.ReadAutoLockTimerPref;
import com.app.xdcpay.Pref.ReadPreferences;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveAutoLockTimerPref;
import com.app.xdcpay.Pref.SavePreferences;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.Pref.SharedPreferenceHelper;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.AppUtility;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewBold;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends BaseActivity implements ImportAccountCallback, IGetUSDValueOfXDCView, BottomSheetInterface, AccountCallbackInterface {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    // private ReadWalletDetails readWalletDetails;
    private ImageView scan, img_copy_walletadd;
    private static TextView wallet_balance;
    private TextView currencyAmount;
    private static TextView wallet_address;
    private static TextView network_name;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    TextViewMedium tvSettings;
    TextViewMedium tvHelp;
    static TextViewMedium account_name, selectedaccountname;
    String xdcBalance = "";
    static String xdcWalletBalance = "";
    private ArrayList<String> list = new ArrayList<>();
    private NetworkAdapter timeLockerAdapter;
    private static List<NetworkEntity> networkLists = new ArrayList<>();
    private ImportedAccountAdapter importedAccountAdapter;
    BottomSheetDialog bottomSheetDialogImport;
    NetworkDataBase networkDataBase;
    AccountEntity accountEntity;
    private CurrencyConversionPresenter currencyConversionPresenter;
    static ReadPreferences readAutoLockTimerPref;
    SavePreferences savePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void getId() {
        //readWalletDetails = new ReadWalletDetails(HomeActivity.this);
        drawerLayout = findViewById(R.id.drawerLayout);
        viewPager = findViewById(R.id.favorite_view_pager);
        currencyAmount = findViewById(R.id.currencyAmount);
        tabLayout = findViewById(R.id.favorite_view_pager_tabs);
        wallet_address = findViewById(R.id.wallet_address);
        wallet_balance = findViewById(R.id.wallet_balance);
        network_name = findViewById(R.id.network_name);
        account_name = findViewById(R.id.account_name);
        selectedaccountname = findViewById(R.id.accountname);
        scan = findViewById(R.id.scan);
        img_copy_walletadd = findViewById(R.id.img_copywalletAd);
        tvSettings = findViewById(R.id.tvSettings);
        tvHelp = findViewById(R.id.tvHelp);
        savePreferences = new SavePreferences(HomeActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.buy).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        findViewById(R.id.receive).setOnClickListener(this);
        findViewById(R.id.menu).setOnClickListener(this);
        findViewById(R.id.view_on_observatory).setOnClickListener(this);
        selectedaccountname.setOnClickListener(this);
        findViewById(R.id.account_rl).setOnClickListener(this);
        findViewById(R.id.accountname).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.tvtransaction).setOnClickListener(this);

        tvSettings.setOnClickListener(this);
        network_name.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        scan.setOnClickListener(this);
    }

    @Override
    public void setData() {
        list.clear();
        Log.e("home_set_data ", "set data called");
        readAutoLockTimerPref = new ReadPreferences(HomeActivity.this);
        networkDataBase = NetworkDataBase.getInstance(HomeActivity.this);
        networkLists = NetworkDataBase.getInstance(getApplicationContext()).getDatabaseDao().getNetworkList();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NFTFragment(), getResources().getString(R.string.nfts));
        adapter.addFragment(new TokensFragment(), getResources().getString(R.string.tokens));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        network_name.setText(readAutoLockTimerPref.getNetworkName());

        list.add(MAIN_NET_NAME);
        list.add(APOTHEM_NAME);
        list.add(LOCALHOST_8545_NAME);
        if (networkLists.size() > 0) {
            for (int i = 0; i < networkLists.size(); i++) {
                list.add(networkLists.get(i).getNetworkName());
            }
        }
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).equals(readAutoLockTimerPref.getNetworkName())) {
                setAccount(HomeActivity.this, getselectedaccount().getId(), null);

            }

        }


        img_copy_walletadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("wallet Address", wallet_address.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(HomeActivity.this, getString(R.string.copied), Toast.LENGTH_LONG).show();
            }
        });

        currencyConversionPresenter = new CurrencyConversionPresenter(this);
        String strSymbol = getString(R.string.txt_xdc);
        Map<String, Object> currencyData = new HashMap<>();
//        currencyData.put("symbol", readWalletDetails.getSelectedCurrency());
        currencyData.put("symbol", strSymbol);
        currencyData.put("CMC_PRO_API_KEY", API_KEY);
        currencyConversionPresenter.onGetUSDValueOfXDC(currencyData, HomeActivity.this, strSymbol);

    }

    @Override
    public void BottomSheetOnClickListener(int pos, String name) {

    }

    public void setAccount(Context context, int id, BottomSheetDialog bottomSheetDialog) {
        List<AccountEntity> accountlist = NetworkDataBase.getInstance(context).getAccountDao().getAccountList();
        for (int i = 0; i < accountlist.size(); i++) {
            if (id == accountlist.get(i).getId()) {
                AccountEntity account = accountlist.get(i);
                if (account.accountName.equals(context.getString(R.string.imported_text))) {
                    account_name.setText(context.getString(R.string.account) + " " + account.getId());
                    selectedaccountname.setText(context.getString(R.string.account) + " " + account.getId());

                } else {
                    account_name.setText(account.accountName);
                    selectedaccountname.setText(account.accountName);
                }
                String add = account.accountAddress;
                if (add.startsWith("0x"))
                    add = add.replaceFirst("0x", "xdc");

                wallet_address.setText(add);
//                selectedaccountname.setText(account.accountName);
                getBalancefromSDK(account);

            }
        }
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
            bottomSheetDialog.dismiss();

    }

    private void getBalancefromSDK(AccountEntity account) {
        XDCpayClient.getInstance().getXdcBalance(account.getAccountAddress(),
                network_name.getText().toString(), true, new EventCallback() {
                    @Override
                    public void success(final String balance) throws Exception {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                xdcWalletBalance = balance;

                                String strInternationalAmount = AppUtility.currencyFormat(xdcWalletBalance);
                                if (networkLists.size() > 0) {
                                    for (int i = 0; i < networkLists.size(); i++) {
                                        Log.e("home_wallet_balance ", xdcWalletBalance + " .. " + networkLists.size()
                                                + ".. " + readAutoLockTimerPref.getNetworkName() + " .. " + networkLists.get(i).getNetworkName());
                                        if (networkLists.get(i).getNetworkName().equals(readAutoLockTimerPref.getNetworkName())) {
                                            wallet_balance.setText(strInternationalAmount + " " + networkLists.get(i).getCurrencySymbol());

                                        } else {
                                            wallet_balance.setText(strInternationalAmount + " " + getString(R.string.txt_xdc));
                                        }
                                    }
                                } else
                                    wallet_balance.setText(strInternationalAmount + " " + getString(R.string.txt_xdc));
                            }
                        });
                    }

                    @Override
                    public void failure(Throwable t) {
                        Log.e("home_wallet_balance t", t.getMessage() + "");
                    }

                    @Override
                    public void failure(String message) {
                        Log.e("home_wallet_balance m", message);
                    }
                });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy:
                startActivity(new Intent(HomeActivity.this, BuyActivity.class));
                finish();
                break;

            case R.id.send:
                startActivity(new Intent(HomeActivity.this, SendActivity.class));
                finish();
                break;

            case R.id.receive:
                startActivity(new Intent(HomeActivity.this, QrCodeActivity.class));
                break;

            case R.id.scan:
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            cameraSource.start(surfaceView.getHolder());
                    Intent i = new Intent(HomeActivity.this, ScannerActivity.class);
                    i.putExtra(ACTIVITY_NAME, "HomeActivity");
                    startActivity(i);
                } else {
                    ActivityCompat.requestPermissions(HomeActivity.this, new
                            String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }

                break;

            case R.id.menu:
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.openDrawer(Gravity.LEFT);
                else
                    drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.account_rl:
                break;

            case R.id.accountname:
                bottomSheetDialogImport = new BottomSheetDialog(HomeActivity.this);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.layout_my_account_dialog);
                TextView createAccount = (TextView) bottomSheetDialogImport.findViewById(R.id.create_account);
                TextView importAccount = (TextView) bottomSheetDialogImport.findViewById(R.id.import_account);
                RecyclerView account_rv = (RecyclerView) bottomSheetDialogImport.findViewById(R.id.account_rv);

                importedAccountAdapter = new ImportedAccountAdapter(getApplicationContext(),
                        NetworkDataBase.getInstance(getApplicationContext()).getAccountDao().getAccountList(), this, bottomSheetDialogImport, this);

                account_rv.setLayoutManager(new LinearLayoutManager(this));
                account_rv.setHasFixedSize(true);
                account_rv.setAdapter(importedAccountAdapter);

//                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                    }
//                }).attachToRecyclerView(account_rv);

                createAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        opencreateAccountDialog();
                        bottomSheetDialogImport.dismiss();
                    }
                });

                importAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Intent intent1 = new Intent(HomeActivity.this, ImportWalletActivity.class);
                        Intent intent1 = new Intent(HomeActivity.this, ImportAccountActivity.class);
                        intent1.putExtra(ACCOUNT_NAME, getString(R.string.imported_text));
                        startActivity(intent1);
                        bottomSheetDialogImport.dismiss();
                        finish();
                    }
                });

                bottomSheetDialogImport.show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.view_on_observatory:
                Intent intent1 = new Intent(HomeActivity.this, BrowserActivity.class);
                intent1.putExtra(Constants.TITLE, getResources().getString(R.string.view_on_observatory));
                intent1.putExtra(Constants.URL, Constants.OBSERVER_URL + NetworkDataBase.getInstance(HomeActivity.this).getAccountDao().getAccountList().get(Integer.parseInt(SharedPreferenceHelper.getSharedPreferenceString(HomeActivity.this, Constants.ACCOUNT, ""))));
                startActivity(intent1);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.tvSettings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                drawerLayout.closeDrawer(Gravity.LEFT);
                finish();
                break;

            case R.id.tvtransaction:
                startActivity(new Intent(HomeActivity.this, TransactionActivity.class));
                drawerLayout.closeDrawer(Gravity.LEFT);

                break;

            case R.id.tvHelp:
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.network_name:

                bottomSheetDialogImport = new BottomSheetDialog(HomeActivity.this);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.fragment_timer_bottom_sheet);
                TextView tvHeader = (TextView) bottomSheetDialogImport.findViewById(R.id.tvHeader);
                RecyclerView rvTimeLocker = (RecyclerView) bottomSheetDialogImport.findViewById(R.id.rvTimeLocker);
                tvHeader.setText(getString(R.string.select_network));

                timeLockerAdapter = new NetworkAdapter();

                rvTimeLocker.setLayoutManager(new LinearLayoutManager(this));
                rvTimeLocker.setHasFixedSize(true);
                rvTimeLocker.setAdapter(timeLockerAdapter);
                bottomSheetDialogImport.show();
                break;

            case R.id.logout:
                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(HomeActivity.this);
                saveWalletDetails.saveIsLogin(false);
                SharedPreferenceHelper.clearSharedPreference(HomeActivity.this);
                Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

        }
    }

    private void opencreateAccountDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeActivity.this);
        bottomSheetDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.layout_add_account_dialog);
        ImageView back = (ImageView) bottomSheetDialog.findViewById(R.id.back);
        EditText account_addname = (EditText) bottomSheetDialog.findViewById(R.id.account_addname);
        TextViewBold addAccount = (TextViewBold) bottomSheetDialog.findViewById(R.id.btn_addAccount);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account_addname.getText().toString().length() == 0) {
                    showtoast(getResources().getString(R.string.add_acc_name));
                } else {
                    File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES +
                            File.separator + "web3j");
                    path.mkdir();
                    XDCpayClient.getInstance().generateWallet(path, "", new CreateAccountCallback() {
                        @Override
                        public void success(WalletData walletData) {
                            if (walletData != null) {

                                accountEntity = new AccountEntity(account_addname.getText().toString(), walletData.getAccountAddress(),
                                        walletData.getPrivateKey(), walletData.getPublickeyKey(), "", ACCOUNT_CREATED);
                                new InsertAccountTask(HomeActivity.this, accountEntity).execute();
                            } else {
                                Toast.makeText(HomeActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(Throwable t) {


                        }

                        @Override
                        public void failure(String message) {

                        }
                    });
                    bottomSheetDialog.dismiss();
                }

            }
        });

        bottomSheetDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0) {
            Intent i = new Intent(HomeActivity.this, ScannerActivity.class);
            i.putExtra(ACTIVITY_NAME, "HomeActivity");
            startActivity(i);
        }
    }

    @Override
    public void AccountListOnClickListener(int pos, List<AccountEntity> networkLists) {

//        intent.putExtra(NETWORK_NAME, networkLists.get(pos).getNetworkName());
//        intent.putExtra(NETWORK_RPC_URL, networkLists.get(pos).getRpcUrl());
//        intent.putExtra(CHAIN_ID, networkLists.get(pos).getChainId());
//        intent.putExtra(CURRENCY_SYMBOL, networkLists.get(pos).getCurrencySymbol());
//        intent.putExtra(BLOCK_EXPLORE_URL, networkLists.get(pos).getBlockExplorerUrl());
//        startActivity(intent);
//        finish();
    }

    @Override
    public void AccountDeleteOnClickListener(int AccountId) {

        verifyAccountDelete(AccountId);

    }

    private void verifyAccountDelete(int AccountID) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeActivity.this);
        bottomSheetDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.layout_delete_account_dialog);
        ImageView back = (ImageView) bottomSheetDialog.findViewById(R.id.back);
        TextViewBold tv_amount = (TextViewBold) bottomSheetDialog.findViewById(R.id.tv_amount);
        TextViewBold btn_deleteAcc = (TextViewBold) bottomSheetDialog.findViewById(R.id.btn_deleteAcc);

        tv_amount.setText(xdcBalance);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

            }
        });

        btn_deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialogImport.dismiss();
                bottomSheetDialog.dismiss();
                new DeleteAccountTask(HomeActivity.this, AccountID).execute();

            }
        });
        bottomSheetDialog.show();
    }

    private class DeleteAccountTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<HomeActivity> activityReference;
        private int AccountID;

        public DeleteAccountTask(HomeActivity addNetworkActivity, int strPrivateKey) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.AccountID = strPrivateKey;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            activityReference.get().networkDataBase.getAccountDao().deleteById(AccountID);

            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
            return null;
        }
    }

    @Override
    public void onGetUSDValueOfXDCFailure(String failure) {
//        Log.e("USDForXDC", failure);
    }

    @Override
    public void onGetUSDValueOfXDCSuccess(double USDValue) {
//        Log.e("USDForXDC", String.valueOf(USDValue));
        updateBalance(USDValue);
    }

    private void updateBalance(double USDValue) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (!TextUtils.isEmpty(xdcWalletBalance)) {
                    if (USDValue > 0) {
                        String strInternationalAmount = String.format(STRING_FORMAT, Double.parseDouble(xdcWalletBalance) * USDValue);

                        currencyAmount.setText(getString(R.string.txt_dollar)+AppUtility.currencyFormat(strInternationalAmount) + TEXT_USD);
                    } else {
                        currencyAmount.setText(getString(R.string.txt_dollar) + "0 " + TEXT_USD);
                    }
                }
            }
        });
    }

    public static class InsertAccountTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<HomeActivity> activityReference;
        private AccountEntity networkEntity;

        public InsertAccountTask(HomeActivity addNetworkActivity, AccountEntity networkEntity) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.networkEntity = networkEntity;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().networkDataBase.getAccountDao().insertAccount(networkEntity);

            return null;
        }
    }

    public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.TimerViewHolder> {
        @NonNull
        @Override
        public NetworkAdapter.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NetworkAdapter.TimerViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_time_locker, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NetworkAdapter.TimerViewHolder holder, int position) {

            holder.network.setText(list.get(position));
            if (readAutoLockTimerPref.getNetworkName().equals(list.get(position))
                    || (network_name.getText().toString().equals(list.get(position))))
                holder.iv_Checked.setVisibility(View.VISIBLE);
            else
                holder.iv_Checked.setVisibility(View.GONE);

            holder.timer_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    savePreferences.saveNetwork(list.get(holder.getAdapterPosition()));
                    if (bottomSheetDialogImport != null)
                        bottomSheetDialogImport.dismiss();
//                    network_name.setText(holder.network.getText().toString());
                    onUserInteraction();
//                    onResume();
                    refreshThisPage();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class TimerViewHolder extends RecyclerView.ViewHolder {
            private com.app.xdcpay.Views.TextView network;
            private ImageView iv_Checked;
            private LinearLayout timer_ll;

            public TimerViewHolder(@NonNull View itemView) {
                super(itemView);
                network = itemView.findViewById(R.id.tvTimer);
                iv_Checked = itemView.findViewById(R.id.iv_Checked);
                timer_ll = itemView.findViewById(R.id.timer_ll);
            }
        }
    }

    private void refreshThisPage() {
//        this.recreate();
        Intent i = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        setData();
    }

    @Override
    public void onAccountClickListener(Context context, int id, BottomSheetDialog bottomSheetDialog) {
        setAccount(context, id, bottomSheetDialog);
        refreshThisPage();
    }
}
