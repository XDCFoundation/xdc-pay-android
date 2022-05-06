package com.app.xdcpay.Fragments.BottomSheet;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.xdcpay.Adapters.TimeLockerAdapter;
import com.app.xdcpay.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class TimerBottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView rvTimeLocker;
    private final ArrayList<Integer> timeLockerList = new ArrayList<>(Arrays.asList(0, 5, 15, 30, 60, 300, 600));
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
        timeLockerAdapter = new TimeLockerAdapter(requireActivity(), timeLockerList);
        rvTimeLocker.setAdapter(timeLockerAdapter);
        return view;
    }

    private void initViews(View v) {
        rvTimeLocker = v.findViewById(R.id.rvTimeLocker);
    }
}