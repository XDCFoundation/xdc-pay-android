package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.XDCJava.XDCpayClient;
import com.XDCJava.callback.EventCallback;
import com.app.xdcpay.Activities.Accounts.ImportAccountActivity;

import com.app.xdcpay.Adapters.ImportedAccountAdapter;
import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Fragments.TokensFragment;
import com.app.xdcpay.Fragments.TransactionsFragment;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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


public class HomeActivity extends BaseActivity implements ImportAccountCallback {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ReadWalletDetails readWalletDetails;
    private ImageView scan, img_copy_walletadd;
    private TextView wallet_balance, amount, wallet_address;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    TextViewMedium tvSettings, tvHelp;

    private ImportedAccountAdapter importedAccountAdapter;
    NetworkDataBase networkDataBase;

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
        tabLayout = findViewById(R.id.favorite_view_pager_tabs);
        wallet_address = findViewById(R.id.wallet_address);
        wallet_balance = findViewById(R.id.wallet_balance);
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
        tvHelp.setOnClickListener(this);
        scan.setOnClickListener(this);
    }

    @Override
    public void setData() {
        networkDataBase = NetworkDataBase.getInstance(HomeActivity.this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionsFragment(), getResources().getString(R.string.transactions));
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
                        wallet_balance.setText(balance + " XDC");
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

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeActivity.this);
                bottomSheetDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialog.setContentView(R.layout.layout_my_account_dialog);
                TextView createAccount = (TextView) bottomSheetDialog.findViewById(R.id.create_account);
                TextView importAccount = (TextView) bottomSheetDialog.findViewById(R.id.import_account);
                RecyclerView account_rv = (RecyclerView) bottomSheetDialog.findViewById(R.id.account_rv);

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
                        bottomSheetDialog.dismiss();
                    }
                });

                importAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Intent intent1 = new Intent(HomeActivity.this, ImportWalletActivity.class);
                        Intent intent1 = new Intent(HomeActivity.this, ImportAccountActivity.class);
                        intent1.putExtra(Constants.TITLE, getResources().getString(R.string.view_on_observatory));
                        intent1.putExtra(Constants.URL, Constants.OBSERVER_URL + readWalletDetails.getAccountAddress());
                        startActivity(intent1);
                        bottomSheetDialog.dismiss();
                        finish();
                    }
                });

                bottomSheetDialog.show();
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
//        WeakReference<HomeActivity> activityReference;
//        activityReference = new WeakReference<>(addNetworkActivity);
//        activityReference.get().networkDataBase.getAccountDao().de(accountEntity);
        new InsertTask(HomeActivity.this, strPrivateKey).execute();
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
}
