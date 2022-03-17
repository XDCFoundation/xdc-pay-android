package com.app.xdcpay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import com.app.xdcpay.Adapters.WelcomePagerAdapter;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextView;

import androidx.viewpager.widget.ViewPager;

public class WelcomeActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout dots_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void getId() {
        viewPager = findViewById(R.id.view_pager);
        dots_ll = findViewById(R.id.dots_ll);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.get_started).setOnClickListener(this);
    }

    @Override
    public void setData() {
        WelcomePagerAdapter pagerAdapter = new WelcomePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        addBottomDots(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_started:
                startActivity(new Intent(WelcomeActivity.this, WalletSetupActivity.class));
                finish();
                break;
        }
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[2];

        dots_ll.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(Color.parseColor("#9FA9BA"));
            dots_ll.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#2149B9"));
    }
}
