package com.app.xdcpay.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xdcpay.Activities.AddTokenActivity;
import com.app.xdcpay.Adapters.TokensAdapter;
import com.app.xdcpay.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TokensFragment extends Fragment implements View.OnClickListener {

    private View v;
    private RecyclerView recyclerView;

    public TokensFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tokens, container, false);
        getViewId();
        setData();
        setListener();
        return v;
    }

    private void setListener() {
        v.findViewById(R.id.add_token).setOnClickListener(this);
    }

    private void getViewId() {
        recyclerView = v.findViewById(R.id.tokens_rv);
    }

    private void setData() {
        TokensAdapter tokensAdapter = new TokensAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(tokensAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_token:
                startActivity(new Intent(getContext(), AddTokenActivity.class));
                break;
        }
    }
}
