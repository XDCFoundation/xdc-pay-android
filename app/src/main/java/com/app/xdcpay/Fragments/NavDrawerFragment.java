package com.app.xdcpay.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.Activities.BrowserActivity;
import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.Activities.SplashActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;

import androidx.fragment.app.Fragment;

public class NavDrawerFragment extends Fragment implements View.OnClickListener {
    View v;
    ReadWalletDetails readWalletDetails;
    TextViewMedium tvSettings, tvHelp;

    public NavDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        getViewId();
        setListener();

        /*tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });*/
        return v;
    }

    private void getViewId() {
        tvSettings = v.findViewById(R.id.tvSettings);
        tvHelp = v.findViewById(R.id.tvHelp);
        readWalletDetails = new ReadWalletDetails(getContext());
    }

    private void setListener() {
        v.findViewById(R.id.view_on_observatory).setOnClickListener(this);
//        v.findViewById(R.id.settings).setOnClickListener(this);
        v.findViewById(R.id.logout).setOnClickListener(this);
        tvSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_on_observatory:
                Intent intent1 = new Intent(getContext(), BrowserActivity.class);
                intent1.putExtra(Constants.TITLE, getResources().getString(R.string.view_on_observatory));
                intent1.putExtra(Constants.URL, Constants.OBSERVER_URL + readWalletDetails.getAccountAddress());
                startActivity(intent1);
                break;

            case R.id.tvSettings:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;

            case R.id.logout:
                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(getContext());
                saveWalletDetails.saveIsLogin(false);
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
