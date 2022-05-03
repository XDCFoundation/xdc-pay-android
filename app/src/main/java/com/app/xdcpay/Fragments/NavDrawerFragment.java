package com.app.xdcpay.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.app.xdcpay.Activities.BrowserActivity;
import com.app.xdcpay.Activities.HelpActivity;
import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.Activities.SplashActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.fragment.app.Fragment;

public class NavDrawerFragment extends Fragment  {
    View v;
    ReadWalletDetails readWalletDetails;
    TextViewMedium tvSettings, tvHelp;

    public NavDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        getViewId();
        return v;
    }

    private void getViewId() {
        tvSettings = v.findViewById(R.id.tvSettings);
        tvHelp = v.findViewById(R.id.tvHelp);
        readWalletDetails = new ReadWalletDetails(getContext());
    }




}
