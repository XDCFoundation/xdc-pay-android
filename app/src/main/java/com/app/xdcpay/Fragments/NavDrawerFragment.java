package com.app.xdcpay.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.Activities.ContactsActivity;
import com.app.xdcpay.Activities.SettingsActivity;
import com.app.xdcpay.R;
import com.app.xdcpay.Views.TextViewMedium;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFragment extends Fragment {
    TextViewMedium tvSettings, tvHelp;

    public NavDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.
                fragment_nav_drawer, container, false);

        tvSettings = v.findViewById(R.id.tvSettings);
        tvHelp = v.findViewById(R.id.tvHelp);

        tvSettings.setOnClickListener(new View.OnClickListener() {
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
        });
        return v;

    }
}
