package com.app.xdcpay.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.Adapters.TransactionsAdapter;
import com.app.xdcpay.DataBase.Entity.ContactEntity;
import com.app.xdcpay.DataBase.Entity.TransactionsEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;

    public TransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_transactions, container, false);
        getViewId();
        setData();
        return v;
    }

    private void getViewId() {
        recyclerView = v.findViewById(R.id.transactions_rv);
    }

    private void setData() {
        List<TransactionsEntity> entity = NetworkDataBase.getInstance(requireActivity()).getTransactionsDao().getTransactionsList();

        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(getContext(),entity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(transactionsAdapter);
    }
}
