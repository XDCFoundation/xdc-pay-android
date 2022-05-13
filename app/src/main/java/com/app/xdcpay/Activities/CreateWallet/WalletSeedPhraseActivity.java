package com.app.xdcpay.Activities.CreateWallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.Adapters.SeedPhraseAdapter;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WalletSeedPhraseActivity extends BaseActivity {
    private GridView gridView;
    private String[] splited;
    private ReadWalletDetails readWalletDetails;
    private List<String> strList = new ArrayList<String>();
    private TextViewMedium tv_copy, title;
    private ImageView back;
    private TextViewMedium btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_seed_phrase);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void getId() {
        gridView = findViewById(R.id.gridView);
        tv_copy = findViewById(R.id.tv_copy);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        btn_continue = findViewById(R.id.btn_continue);
        readWalletDetails = new ReadWalletDetails(WalletSeedPhraseActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        tv_copy.setOnClickListener(this);
        back.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.secure_wallet));
        splited = readWalletDetails.getSeedPhrase().split("\\s+");
        strList = Arrays.asList(splited);

        gridView.setAdapter(new SeedPhraseAdapter(this, strList, 1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_copy:
                ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Secret Seed Phrase", readWalletDetails.getSeedPhrase());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), getString(R.string.copy_to_clipboard), Toast.LENGTH_LONG).show();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.btn_continue:
                SaveWalletDetails saveWalletDetails = new SaveWalletDetails(WalletSeedPhraseActivity.this);
                saveWalletDetails.IsSeedPhaseConfirm(false);
                startActivity(new Intent(WalletSeedPhraseActivity.this, ConfirmSeedPhraseActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}