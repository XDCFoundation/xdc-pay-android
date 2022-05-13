package com.app.xdcpay.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

import androidx.fragment.app.Fragment;

public class NavDrawerFragment extends Fragment  {
    View v;
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
    }




}
