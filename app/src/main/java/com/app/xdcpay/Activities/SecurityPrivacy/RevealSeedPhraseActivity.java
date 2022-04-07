package com.app.xdcpay.Activities.SecurityPrivacy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.Adapters.SeedPhraseAdapter;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RevealSeedPhraseActivity extends BaseActivity {
    GridView gridView;
    private String[] splited;
    private ReadWalletDetails readWalletDetails;
    private List<String> strList = new ArrayList<String>();
    private TextViewMedium tv_copy, title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_seed_phrase);
    }

    @Override
    public void getId() {
        gridView = findViewById(R.id.gridView);
        tv_copy = findViewById(R.id.tv_copy);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        readWalletDetails = new ReadWalletDetails(RevealSeedPhraseActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        tv_copy.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getString(R.string.secret_seed));
//        if (user_wallet != null && user_wallet.getSeedPhrase() != null
//                && user_wallet.getSeedPhrase().length() > 0) {
        splited = readWalletDetails.getSeedPhrase().split("\\s+");
        strList = Arrays.asList(splited);
//        }

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
        }
    }
}