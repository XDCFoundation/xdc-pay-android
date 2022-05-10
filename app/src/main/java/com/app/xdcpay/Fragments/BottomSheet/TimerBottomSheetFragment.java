package com.app.xdcpay.Fragments.BottomSheet;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Adapters.TimeLockerAdapter;
import com.app.xdcpay.Interface.BottomSheetInterface;
import com.app.xdcpay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class TimerBottomSheetFragment extends BottomSheetDialogFragment implements BottomSheetInterface {

    private RecyclerView rvTimeLocker;
    private final ArrayList<String> timeLockerList = new ArrayList<>(Arrays.asList("Immediately", "5 seconds", "15 seconds", "30 seconds",
            "60 seconds", "5 minutes", "10 minutes"));
    private TimeLockerAdapter timeLockerAdapter;
    private LinearLayoutManager layoutManager;

    public TimerBottomSheetFragment() {
        // Required empty public constructor
    }

    public static TimerBottomSheetFragment newInstance(String param1, String param2) {
        TimerBottomSheetFragment fragment = new TimerBottomSheetFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_bottom_sheet, container, false);
        initViews(view);

        layoutManager = new LinearLayoutManager(getActivity());
        rvTimeLocker.setLayoutManager(layoutManager);
        timeLockerAdapter = new TimeLockerAdapter(timeLockerList, this);
        rvTimeLocker.setAdapter(timeLockerAdapter);
        return view;
    }

    private void initViews(View v) {
        rvTimeLocker = v.findViewById(R.id.rvTimeLocker);

    }

    @Override
    public void BottomSheetOnClickListener(int pos, String name) {

    }
}