package com.app.xdcpay.Activities.SecurityPrivacy;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.xdcpay.Pref.ReadAutoLockTimerPref;
import com.app.xdcpay.Pref.SaveAutoLockTimerPref;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewBold;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class SecurityAndPrivacyActivity extends BaseActivity {
    private final ArrayList<Integer> timeLockerList = new ArrayList<>(Arrays.asList(0, 5, 15, 30, 60, 300, 600));
    private TextViewBold btn_revealSeedPhrase, btn_changePassword, btn_showPrivateKey;
    private SwitchCompat switch_btn;
    private TextView tv_switch;
    private TextViewMedium title;
    private ImageView back;
    private EditText edit_timer;
    private ReadAutoLockTimerPref readAutoLockTimerPref;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_and_privacy);
    }

    @Override
    public void getId() {
        readAutoLockTimerPref = new ReadAutoLockTimerPref(SecurityAndPrivacyActivity.this);
        btn_revealSeedPhrase = findViewById(R.id.btn_revealSeedPhrase);
        btn_changePassword = findViewById(R.id.btn_changePassword);
        btn_showPrivateKey = findViewById(R.id.btn_showPrivateKey);
        switch_btn = findViewById(R.id.switch_btn);
        tv_switch = findViewById(R.id.tv_switch);
        title = findViewById(R.id.title);
        edit_timer = findViewById(R.id.edit_timer);
        back = findViewById(R.id.back);
        setData();
    }

    @Override
    public void setListener() {
        btn_revealSeedPhrase.setOnClickListener(this);
        btn_changePassword.setOnClickListener(this);
        btn_showPrivateKey.setOnClickListener(this);
        btn_showPrivateKey.setOnClickListener(this);
        edit_timer.setOnClickListener(this);
        back.setOnClickListener(this);

        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    tv_switch.setText(getString(R.string.text_on));
                else
                    tv_switch.setText(getString(R.string.text_off));
            }
        });
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.security_privacy_settings));

        if (readAutoLockTimerPref.getTimer() > 60)
            edit_timer.setText(getResources().getString(
                    R.string.minutes,
                    String.valueOf(readAutoLockTimerPref.getTimer() / 60)));
        else
            edit_timer.setText(getResources().getString(
                    R.string.seconds,
                    String.valueOf(readAutoLockTimerPref.getTimer())));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_revealSeedPhrase:
                Intent i = new Intent(SecurityAndPrivacyActivity.this, SeedPhrasePasswordActivity.class);
                startActivity(i);
                break;
            case R.id.btn_changePassword:
                Intent intent = new Intent(SecurityAndPrivacyActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_showPrivateKey:
                Intent privateKeyIntent = new Intent(SecurityAndPrivacyActivity.this, PrivateKeyActivity.class);
                startActivity(privateKeyIntent);
                break;
            case R.id.edit_timer:
//                TimerBottomSheetFragment fragment = new TimerBottomSheetFragment();
//                Bundle b = new Bundle();
//                b.putString("contentId", edit_timer.getText().toString());
////                b.putString("createdByUserId", data.getContents().getCreatedBy().getUserId());
////                b.putString("ipfsUrl", data.getContents().getIpfsUrl());
////                b.putInt("position", itemPos);
////                b.putInt("commentSize", data.getCommentsSize());
//                fragment.setArguments(b);
//                fragment.show(getSupportFragmentManager(), "TAG");

                bottomSheetDialog = new BottomSheetDialog(SecurityAndPrivacyActivity.this);
                bottomSheetDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialog.setContentView(R.layout.layout_timer_bottom_sheet_dialog);
                RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.rvTimeLocker);
                TimerAdapter timerAdapter = new TimerAdapter();
                recyclerView.setLayoutManager(new LinearLayoutManager(SecurityAndPrivacyActivity.this));
                recyclerView.setAdapter(timerAdapter);
                bottomSheetDialog.show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerViewHolder> {
        @NonNull
        @Override
        public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TimerViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_time_locker, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {
            ReadAutoLockTimerPref readAutoLockTimerPref = new ReadAutoLockTimerPref(SecurityAndPrivacyActivity.this);
            if (readAutoLockTimerPref.getTimer() == timeLockerList.get(position))
                holder.iv_Checked.setVisibility(View.VISIBLE);
            else
                holder.iv_Checked.setVisibility(View.GONE);
            if (timeLockerList.get(position) > 60)
                holder.timer.setText(getResources().getString(
                        R.string.minutes,
                        String.valueOf(timeLockerList.get(position) / 60)));
            else
                holder.timer.setText(getResources().getString(
                        R.string.seconds,
                        String.valueOf(timeLockerList.get(position))));
            holder.timer_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SaveAutoLockTimerPref saveAutoLockTimerPref = new SaveAutoLockTimerPref(SecurityAndPrivacyActivity.this);
                    saveAutoLockTimerPref.saveTime(timeLockerList.get(holder.getAdapterPosition()));
                    if (bottomSheetDialog != null)
                        bottomSheetDialog.dismiss();
                    edit_timer.setText(holder.timer.getText().toString());
                    onUserInteraction();
                }
            });
        }

        @Override
        public int getItemCount() {
            return timeLockerList.size();
        }

        public class TimerViewHolder extends RecyclerView.ViewHolder {
            private TextView timer;
            private ImageView iv_Checked;
            private LinearLayout timer_ll;

            public TimerViewHolder(@NonNull View itemView) {
                super(itemView);
                timer = itemView.findViewById(R.id.tvTimer);
                iv_Checked = itemView.findViewById(R.id.iv_Checked);
                timer_ll = itemView.findViewById(R.id.timer_ll);
            }
        }
    }
}