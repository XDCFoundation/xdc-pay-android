package com.app.xdcpay.Activities;

import static com.app.xdcpay.Utils.Constants.DELAY_MS;
import static com.app.xdcpay.Utils.Constants.PERIOD_MS;
import static com.app.xdcpay.Utils.Constants.imageId;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.xdcpay.Adapters.WelcomePagerAdapter;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private ViewPager viewPager;
    private LinearLayout dots_ll;

    private int dotscount;
    private ImageView[] dots;
    private Timer timer;
    private int currentPage = 0;
//    LinearLayout SliderDots;

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
        WelcomePagerAdapter pagerAdapter = new WelcomePagerAdapter(imageId, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
//        addBottomDots(0);

        dotscount = pagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_not_filled));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            dots_ll.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filled));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_not_filled));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_filled));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

       /* viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });*/

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

  /*  private void addBottomDots(int currentPage) {
        ImageView[] dots = new ImageView[2];

        dots_ll.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.ic_not_filled);
            dots[i].setPadding(10, 0, 10, 0);
            dots_ll.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setImageResource(R.drawable.ic_filled);
    }*/

    private void addBottomDots(int currentPage) {


    }
}
