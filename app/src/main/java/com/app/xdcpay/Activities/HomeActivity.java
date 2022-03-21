package com.app.xdcpay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.xdcpay.Fragments.TokensFragment;
import com.app.xdcpay.Fragments.TransactionsFragment;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class HomeActivity extends BaseActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void getId() {
        viewPager = findViewById(R.id.favorite_view_pager);
        tabLayout = findViewById(R.id.favorite_view_pager_tabs);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.buy).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        findViewById(R.id.receive).setOnClickListener(this);
    }

    @Override
    public void setData() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionsFragment(), getResources().getString(R.string.transactions));
        adapter.addFragment(new TokensFragment(), getResources().getString(R.string.tokens));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
        }
    }
}
