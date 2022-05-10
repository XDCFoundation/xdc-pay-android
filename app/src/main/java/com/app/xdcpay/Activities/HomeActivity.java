package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;
import static com.app.xdcpay.Utils.ApiConstants.API_KEY;
import static com.app.xdcpay.Utils.Constants.ACCOUNT_NAME;
import static com.app.xdcpay.Utils.Constants.APOTHEM_NAME;
import static com.app.xdcpay.Utils.Constants.LOCALHOST_8545_NAME;
import static com.app.xdcpay.Utils.Constants.MAIN_NET_NAME;
import static com.app.xdcpay.Utils.Constants.STRING_FORMAT;
import static com.app.xdcpay.Utils.Constants.TEXT_USD;

import android.Manifest;
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
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.XDCJava.Model.WalletData;
import com.XDCJava.XDCpayClient;
import com.XDCJava.callback.CreateAccountCallback;
import com.XDCJava.callback.EventCallback;
import com.app.xdcpay.Activities.Accounts.ImportAccountActivity;

import com.app.xdcpay.Activities.CreateWallet.WalletSeedPhraseActivity;
import com.app.xdcpay.Activities.Settings.GeneralSettingsActivity;
import com.app.xdcpay.Adapters.ImportedAccountAdapter;
import com.app.xdcpay.Adapters.TimeLockerAdapter;
import com.app.xdcpay.Api.Presenter.CurrencyConversionPresenter;
import com.app.xdcpay.Api.View.IGetUSDValueOfXDCView;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Fragments.TokensFragment;
import com.app.xdcpay.Fragments.TransactionsFragment;
import com.app.xdcpay.Interface.BottomSheetInterface;
import com.app.xdcpay.Interface.ImportAccountCallback;
import com.app.xdcpay.Fragments.NFTFragment;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
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

public class HomeActivity extends BaseActivity implements ImportAccountCallback, IGetUSDValueOfXDCView, BottomSheetInterface {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ReadWalletDetails readWalletDetails;
    private ImageView scan, img_copy_walletadd;
    private TextView wallet_balance, currencyAmount, wallet_address, network_name;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    TextViewMedium tvSettings, tvHelp;
    String xdcBalance = "";
    String xdcWalletBalance = "";
    private ArrayList<String> list = new ArrayList<>();
    private TimeLockerAdapter timeLockerAdapter;
    private List<NetworkEntity> networkLists = new ArrayList<>();
    private ImportedAccountAdapter importedAccountAdapter;
    BottomSheetDialog bottomSheetDialogImport;
    NetworkDataBase networkDataBase;
    private CurrencyConversionPresenter currencyConversionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void getId() {
        readWalletDetails = new ReadWalletDetails(HomeActivity.this);
        drawerLayout = findViewById(R.id.drawerLayout);
        viewPager = findViewById(R.id.favorite_view_pager);
        currencyAmount = findViewById(R.id.currencyAmount);
        tabLayout = findViewById(R.id.favorite_view_pager_tabs);
        wallet_address = findViewById(R.id.wallet_address);
        wallet_balance = findViewById(R.id.wallet_balance);
        network_name = findViewById(R.id.network_name);
        scan = findViewById(R.id.scan);
        img_copy_walletadd = findViewById(R.id.img_copywalletAd);
        tvSettings = findViewById(R.id.tvSettings);
        tvHelp = findViewById(R.id.tvHelp);
        setData();

    }

    @Override
    public void setListener() {
        findViewById(R.id.buy).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        findViewById(R.id.receive).setOnClickListener(this);
        findViewById(R.id.menu).setOnClickListener(this);
        findViewById(R.id.view_on_observatory).setOnClickListener(this);
        findViewById(R.id.accountname).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        network_name.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        scan.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkDataBase = NetworkDataBase.getInstance(HomeActivity.this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NFTFragment(), getResources().getString(R.string.nfts));
        adapter.addFragment(new TokensFragment(), getResources().getString(R.string.tokens));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        XDCpayClient.getInstance().getXdcBalance(readWalletDetails.getAccountAddress(), Constants.CONNECTED_NETWORK, new EventCallback() {
            @Override
            public void success(final String balance) throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wallet_address.setText(readWalletDetails.getAccountAddress());
                        xdcWalletBalance = balance;
                        xdcBalance = balance + " " + getString(R.string.txt_xdc);
                        wallet_balance.setText(xdcBalance);
                    }
                });
            }

            @Override
            public void failure(Throwable t) {
                Log.e("get balance t", t.getMessage() + "");
            }

            @Override
            public void failure(String message) {
                Log.e("get balance msg", message);
            }
        });

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            cameraSource.start(surfaceView.getHolder());
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
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
        currencyConversionPresenter.onGetUSDValueOfXDC(currencyData, HomeActivity.this,strSymbol);

    }

    @Override
    public void BottomSheetOnClickListener(int pos, String name) {

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
                break;

            case R.id.send:
                startActivity(new Intent(HomeActivity.this, SendActivity.class));
                break;

            case R.id.receive:
                startActivity(new Intent(HomeActivity.this, QrCodeActivity.class));
                break;

            case R.id.scan:
                Intent i = new Intent(HomeActivity.this, ScannerActivity.class);
                i.putExtra(ACTIVITY_NAME, "HomeActivity");
                startActivity(i);
                break;

            case R.id.menu:
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.openDrawer(Gravity.LEFT);
                else
                    drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.accountname:
                bottomSheetDialogImport = new BottomSheetDialog(HomeActivity.this);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.layout_my_account_dialog);
                TextView createAccount = (TextView) bottomSheetDialogImport.findViewById(R.id.create_account);
                TextView importAccount = (TextView) bottomSheetDialogImport.findViewById(R.id.import_account);
                RecyclerView account_rv = (RecyclerView) bottomSheetDialogImport.findViewById(R.id.account_rv);

                importedAccountAdapter = new ImportedAccountAdapter(getApplicationContext(),
                        NetworkDataBase.getInstance(getApplicationContext()).getAccountDao().getAccountList(), this);

                account_rv.setLayoutManager(new LinearLayoutManager(this));
                account_rv.setHasFixedSize(true);
                account_rv.setAdapter(importedAccountAdapter);

                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    }
                }).attachToRecyclerView(account_rv);

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
                intent1.putExtra(Constants.URL, Constants.OBSERVER_URL + readWalletDetails.getAccountAddress());
                startActivity(intent1);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.tvSettings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                drawerLayout.closeDrawer(Gravity.LEFT);

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
                list.clear();
                bottomSheetDialogImport = new BottomSheetDialog(HomeActivity.this);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.fragment_timer_bottom_sheet);
                TextView tvHeader = (TextView) bottomSheetDialogImport.findViewById(R.id.tvHeader);
                RecyclerView rvTimeLocker = (RecyclerView) bottomSheetDialogImport.findViewById(R.id.rvTimeLocker);
                tvHeader.setText(getString(R.string.select_network));

                chekNetworkInDB();

                rvTimeLocker.setLayoutManager(new LinearLayoutManager(this));
                rvTimeLocker.setHasFixedSize(true);
                rvTimeLocker.setAdapter(timeLockerAdapter);
                bottomSheetDialogImport.show();
                break;

            case R.id.logout:
                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(HomeActivity.this);
                saveWalletDetails.saveIsLogin(false);
                Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

        }
    }

    private void chekNetworkInDB() {
        list.add(MAIN_NET_NAME);
        list.add(APOTHEM_NAME);
        list.add(LOCALHOST_8545_NAME);
        networkLists = NetworkDataBase.getInstance(getApplicationContext()).getDatabaseDao().getNetworkList();
        if (networkLists.size() > 0) {
            for (int i = 0; i <= networkLists.size(); i++) {
                list.add(networkLists.get(i).getNetworkName());
            }
        }
        timeLockerAdapter = new TimeLockerAdapter(list, this);
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
                                Toast.makeText(HomeActivity.this, walletData.getAccountAddress(), Toast.LENGTH_SHORT).show();
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
    public void AccountDeleteOnClickListener(String strPrivateKey) {

        verifyAccountDelete(strPrivateKey);

    }

    private void verifyAccountDelete(String strPrivateKey) {
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
                new InsertTask(HomeActivity.this, strPrivateKey).execute();

            }
        });
        bottomSheetDialog.show();
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<HomeActivity> activityReference;
        private String strPrivateKey;

        public InsertTask(HomeActivity addNetworkActivity, String strPrivateKey) {
            activityReference = new WeakReference<>(addNetworkActivity);
            this.strPrivateKey = strPrivateKey;
            this.strPrivateKey = strPrivateKey;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            activityReference.get().networkDataBase.getAccountDao().deleteById(strPrivateKey);
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
            @Override
            public void run() {

                if (!TextUtils.isEmpty(xdcWalletBalance)) {
                    if (USDValue > 0) {
                        currencyAmount.setText(getString(R.string.txt_dollar)
                                + String.format(STRING_FORMAT, Double.parseDouble(xdcWalletBalance) * USDValue)
                                + TEXT_USD);
                    }
                }
            }
        });
    }
}
