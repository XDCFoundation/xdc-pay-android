package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.XDCJava.FleekClient;
import com.XDCJava.callback.EventCallback;
import com.app.xdcpay.Fragments.TokensFragment;
import com.app.xdcpay.Fragments.TransactionsFragment;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class HomeActivity extends BaseActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ReadWalletDetails readWalletDetails;
    private ImageView scan;
    private TextView wallet_balance, amount, wallet_address;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

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

        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.buy).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        findViewById(R.id.receive).setOnClickListener(this);
        findViewById(R.id.menu).setOnClickListener(this);
        scan.setOnClickListener(this);
    }

    @Override
    public void setData() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionsFragment(), getResources().getString(R.string.transactions));
        adapter.addFragment(new TokensFragment(), getResources().getString(R.string.tokens));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        FleekClient.getInstance().getXdcBalance(readWalletDetails.getAccountAddress(), Constants.CONNECTED_NETWORK, new EventCallback() {
            @Override
            public void success(String balance) throws Exception {
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
        }
    }
}
