package com.app.xdcpay.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.Adapters.NFTsAdapter;
import com.app.xdcpay.Adapters.TransactionsAdapter;
import com.app.xdcpay.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NFTFragment extends Fragment {
    private View v;
    private RecyclerView nft_rv;

    public NFTFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_nft, container, false);
        getViewId();
        setData();
        return v;
    }

    private void getViewId() {
        nft_rv = v.findViewById(R.id.nft_rv);
    }

    private void setData() {
        NFTsAdapter transactionsAdapter = new NFTsAdapter(getContext());
        nft_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        nft_rv.setAdapter(transactionsAdapter);
    }
}
