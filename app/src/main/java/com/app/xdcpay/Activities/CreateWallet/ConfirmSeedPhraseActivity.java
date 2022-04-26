package com.app.xdcpay.Activities.CreateWallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.Activities.CreateWalletActivity;
import com.app.xdcpay.Activities.HomeActivity;
import com.app.xdcpay.Activities.ImportWalletActivity;
import com.app.xdcpay.Activities.SplashActivity;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SaveWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConfirmSeedPhraseActivity extends BaseActivity {
    final int min = 1;
    final int max = 12;
    int randomNo, randomNo2, randomNo3;
    private TextViewMedium text1, text2, text3, confirm_recovery_password;
    private EditText etWord1, etWord2, etWord3;
    private ReadWalletDetails readWalletDetails;
    private String[] splited;
    private String seedText1, seedText2, seedText3;
    private List<String> strList = new ArrayList<String>();
    private ImageView ivWord1, ivWord2, ivWord3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_seed_phrase);
    }

    @Override
    public void getId() {
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        etWord1 = findViewById(R.id.etWord1);
        etWord2 = findViewById(R.id.etWord2);
        etWord3 = findViewById(R.id.etWord3);
        ivWord1 = findViewById(R.id.ivWord1);
        ivWord2 = findViewById(R.id.ivWord2);
        ivWord3 = findViewById(R.id.ivWord3);
        confirm_recovery_password = findViewById(R.id.confirm_recovery_password);
        readWalletDetails = new ReadWalletDetails(ConfirmSeedPhraseActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        confirm_recovery_password.setOnClickListener(this);
        etWord1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(seedText1)) {
                    ivWord1.setVisibility(View.VISIBLE);
                } else
                    ivWord1.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* if (editable.equals(seedText1)) {
                    Toast.makeText(ConfirmSeedPhraseActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    ivWord1.setVisibility(View.VISIBLE);
                } else ivWord1.setVisibility(View.GONE);*/
            }
        });

        etWord2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(seedText2)) {
                    ivWord2.setVisibility(View.VISIBLE);
                } else
                    ivWord2.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etWord3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(seedText3)) {
                    ivWord3.setVisibility(View.VISIBLE);
                } else
                    ivWord3.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setData() {

        randomNo = new Random().nextInt((max - min) + 1) + min;
        randomNo2 = new Random().nextInt((max - min) + 1) + min;
        randomNo3 = new Random().nextInt((max - min) + 1) + min;

        int number[] = {randomNo, randomNo2, randomNo3};
        Arrays.sort(number);
        for (int i = 0; i < 3; i++)
            if (i == 0) {
                randomNo = number[i];
            } else if (i == 1) {
                randomNo2 = number[i];
            } else {
                randomNo3 = number[i];
            }
        Log.d("randomNo", " .." + randomNo + " .. " + randomNo2 + " .. " + randomNo3);
        text1.setText(getString(R.string.word) + " " + randomNo);
        text2.setText(getString(R.string.word) + " " + randomNo2);
        text3.setText(getString(R.string.word) + " " + randomNo3);

        splited = readWalletDetails.getSeedPhrase().split("\\s+");
        strList = Arrays.asList(splited);
        seedText1 = strList.get(randomNo - 1);
        seedText2 = strList.get(randomNo2 - 1);
        seedText3 = strList.get(randomNo3 - 1);
        Log.d("randomNo", "  1 .." + seedText1 + " .. " + seedText2 + " .. " + seedText3 + "\n" + splited);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_recovery_password:
                if (isValid()) {
                    SaveWalletDetails saveWalletDetails = new SaveWalletDetails(ConfirmSeedPhraseActivity.this);
                    saveWalletDetails.IsSeedPhaseConfirm(true);
                    saveWalletDetails.saveIsLogin(true);
                    startActivity(new Intent(ConfirmSeedPhraseActivity.this, HomeActivity.class));
                    finish();
                }
        }
    }

    private boolean isValid() {
        if (!Validations.hasText(etWord1))
            etWord1.setError(getResources().getString(R.string.error_empty));
        else if (!Validations.hasText(etWord2))
            etWord2.setError(getResources().getString(R.string.error_empty));
        else if (!Validations.hasText(etWord3))
            etWord3.setError(getResources().getString(R.string.error_empty));
        else if (!etWord1.getText().toString().equals(seedText1))
            etWord1.setError(getResources().getString(R.string.wrong_secret_phrase));
        else if (!etWord2.getText().toString().equals(seedText2))
            etWord2.setError(getResources().getString(R.string.wrong_secret_phrase));
        else if (!etWord3.getText().toString().equals(seedText3))
            etWord3.setError(getResources().getString(R.string.wrong_secret_phrase));
        else return true;

        return false;
    }

}