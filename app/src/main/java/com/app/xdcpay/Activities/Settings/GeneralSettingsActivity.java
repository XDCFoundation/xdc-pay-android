package com.app.xdcpay.Activities.Settings;

import static com.app.xdcpay.Utils.Constants.currencyList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.Adapters.TimeLockerAdapter;
import com.app.xdcpay.Interface.BottomSheetInterface;
import com.app.xdcpay.Pref.SavePreferences;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class GeneralSettingsActivity extends BaseActivity implements BottomSheetInterface {
    private TextViewMedium title;
    private TimeLockerAdapter timeLockerAdapter;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList("USD- United State Dollar"));
    BottomSheetDialog bottomSheetDialogImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);
        setData();
    }

    @Override
    public void setListener() {

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.currency).setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.general_settings));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.currency:
                bottomSheetDialogImport = new BottomSheetDialog(GeneralSettingsActivity.this);
                bottomSheetDialogImport.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialogImport.setContentView(R.layout.fragment_timer_bottom_sheet);
                TextView tvHeader = (TextView) bottomSheetDialogImport.findViewById(R.id.tvHeader);
                RecyclerView rvTimeLocker = (RecyclerView) bottomSheetDialogImport.findViewById(R.id.rvTimeLocker);
                tvHeader.setText(getString(R.string.base_Currency));
                timeLockerAdapter = new TimeLockerAdapter(currencyList, this);

                rvTimeLocker.setLayoutManager(new LinearLayoutManager(this));
                rvTimeLocker.setHasFixedSize(true);
                rvTimeLocker.setAdapter(timeLockerAdapter);

                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    }
                }).attachToRecyclerView(rvTimeLocker);

                bottomSheetDialogImport.show();

                break;
        }
    }

    @Override
    public void BottomSheetOnClickListener(int pos, String name) {
        SavePreferences saveWalletDetails = new SavePreferences(GeneralSettingsActivity.this);
        saveWalletDetails.saveSelectedCurrency(name);
        bottomSheetDialogImport.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(GeneralSettingsActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}