package com.app.xdcpay.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.xdcpay.R;

import androidx.viewpager.widget.PagerAdapter;

public class WelcomePagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Activity activity;
    private Integer[] imagesArray;

    public WelcomePagerAdapter(Integer[] imagesArray, Activity activity) {
        this.activity = activity;
        this.imagesArray = imagesArray;
    }

    @Override
    public int getCount() {
        return imagesArray.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(activity);

        View view = layoutInflater.inflate(R.layout.layout_welcome_viewpager, container, false);
        ImageView im_slider = view.findViewById(R.id.welcome_iv);
        TextView title = view.findViewById(R.id.welcome_title_tv);
        TextView msg = view.findViewById(R.id.welcome_msg_tv);
        im_slider.setBackgroundResource(imagesArray[position]);
        if (position == 0) {
//            im_slider.setImageResource(R.drawable.ic_illustration);
            title.setText(R.string.welcome_to_xdc_pay);
        } else {
//            im_slider.setImageResource(R.drawable.ic_illustration_1);
            title.setText(R.string.manage_digital_assets);
        }
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        View view = (View) object;
        container.removeView((View) object);
    }
}
