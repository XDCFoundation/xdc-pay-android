package com.app.xdcpay.Activities;

import static com.app.xdcpay.Activities.ScannerActivity.ACTIVITY_NAME;
import static com.app.xdcpay.Activities.ScannerActivity.ADDRESS;
import static com.app.xdcpay.Utils.ApiConstants.API_KEY;
import static com.app.xdcpay.Utils.Constants.STRING_FORMAT;
import static com.app.xdcpay.Utils.Constants.TEXT_USD;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.XDCAndroid.XDC20Client;
import com.XDCAndroid.XDC721Client;
import com.XDCJava.XDCpayClient;
import com.XDCJava.callback.EventCallback;
import com.app.xdcpay.Activities.Settings.AdvanceSettings;
import com.app.xdcpay.Pref.ReadPreferences;
import com.app.xdcpay.Api.Presenter.CurrencyConversionPresenter;
import com.app.xdcpay.Api.View.IGetUSDValueOfXDCView;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.Pref.SavePreferences;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.AppUtility;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;
import com.app.xdcpay.Utils.Validations;
import com.app.xdcpay.Views.EditText;
import com.app.xdcpay.Views.TextViewMedium;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends BaseActivity implements IGetUSDValueOfXDCView {
    private ImageView iv_barcode;
    private EditText etReceiverAddress, etSenderAddress, etAmount, etGasPrice, etGasLimit, etNote;
    private String strAddress;
    private ReadWalletDetails readWalletDetails;
    ReadPreferences readNetworkPref;
    private TextView btn_next, availableBalance, tv_usd;
    private String bal_str;
    private TextViewMedium tvGasLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
    }

    @Override
    public void getId() {
        btn_next = findViewById(R.id.btn_next);
        availableBalance = findViewById(R.id.availableBalance);
        iv_barcode = findViewById(R.id.iv_barcode);
        etReceiverAddress = findViewById(R.id.etReceiverAddress);
        etSenderAddress = findViewById(R.id.etSenderAddress);
        etGasPrice = findViewById(R.id.etGasPrice);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        tv_usd = findViewById(R.id.tv_usd);
        etGasLimit = findViewById(R.id.etGasLimit);
        tvGasLimit = findViewById(R.id.tvGasLimit);
        readWalletDetails = new ReadWalletDetails(SendActivity.this);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        iv_barcode.setOnClickListener(this);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bal_str != null) {
                    if (Validations.hasText(bal_str) && Validations.hasText(etAmount)) {
                        double b = Double.parseDouble(bal_str);
                        double a = Double.parseDouble(etAmount.getText().toString());
                        CurrencyConversionPresenter currencyConversionPresenter = new CurrencyConversionPresenter(SendActivity.this);
                        String strSymbol = getString(R.string.txt_xdc);
                        Map<String, Object> currencyData = new HashMap<>();
                        currencyData.put("symbol", strSymbol);
                        currencyData.put("CMC_PRO_API_KEY", API_KEY);
                        currencyConversionPresenter.onGetUSDValueOfXDC(currencyData, SendActivity.this, strSymbol);

                    }
                } else onResume();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setData() {
        readNetworkPref = new ReadPreferences(SendActivity.this);
        if (readNetworkPref.getIsGasPriceSelected()) {
            etGasLimit.setClickable(true);
            etGasLimit.setFocusable(true);
            etGasPrice.setFocusable(true);
            etGasPrice.setFocusable(true);
        } else {
            etGasLimit.setClickable(false);
            etGasLimit.setFocusable(false);
            etGasPrice.setFocusable(false);
            etGasPrice.setFocusable(false);
        }
        Intent i = getIntent();
        if (i != null) {
            strAddress = i.getStringExtra(ADDRESS);
            if (strAddress != null && Validations.hasText(strAddress))
                etReceiverAddress.setText(strAddress);
        }
        String add = getselectedaccount().getAccountAddress();
        if (add.startsWith("0x"))
            add = add.replaceFirst("0x", "xdc");
        etSenderAddress.setText(add);

        etGasLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strGasLimit = etGasLimit.getText().toString();
                if (strGasLimit.equals("")) {
                    tvGasLimit.setVisibility(View.VISIBLE);
                } else {
                    int gasLimit = Integer.parseInt(strGasLimit);
                    if (!Validations.hasGasLimit(gasLimit))
                        tvGasLimit.setVisibility(View.VISIBLE);
                    else
                        tvGasLimit.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.iv_barcode:
                Intent intent = new Intent(SendActivity.this, ScannerActivity.class);
                intent.putExtra(ACTIVITY_NAME, "SendActivity");
                startActivity(intent);
                break;

            case R.id.btn_next:
                if (isValid()) {
                    Intent i = new Intent(SendActivity.this, ConfirmTransactionActivity.class);
                    i.putExtra(Constants.SENDER, etSenderAddress.getText().toString());
                    i.putExtra(Constants.RECEIVER, etReceiverAddress.getText().toString());
                    i.putExtra(Constants.GAS_PRICE, etGasPrice.getText().toString());
                    i.putExtra(Constants.GAS_LIMIT, etGasLimit.getText().toString());
                    i.putExtra(Constants.AMOUNT, etAmount.getText().toString());
                    startActivity(i);
                    break;
                }
        }
    }

    private boolean isValid() {
        String strGasLimit = etGasLimit.getText().toString();
        int gasLimit = Integer.parseInt(strGasLimit);
        if (!Validations.hasText(etReceiverAddress))
            etReceiverAddress.setError(getResources().getString(R.string.recipient_address_required));
        else if (!Validations.hasText(etSenderAddress))
            etSenderAddress.setError(getResources().getString(R.string.sender_address_required));
        else if (!Validations.hasText(etGasPrice))
            etGasPrice.setError(getResources().getString(R.string.gas_price_required));
        else if (!Validations.hasText(etAmount))
            etAmount.setError(getResources().getString(R.string.amount_required));
        else if (strGasLimit.equals(""))
            etGasLimit.setError(getString(R.string.gas_limit_required));
        else if (!Validations.hasGasLimit(gasLimit))
            tvGasLimit.setVisibility(View.VISIBLE);
        else
            return true;

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        readWalletDetails = new ReadWalletDetails(SendActivity.this);

        XDCpayClient.getInstance().getXdcBalance(getselectedaccount().getAccountAddress(), readNetworkPref.getNetworkName(), true,
                new EventCallback() {
                    @Override
                    public void success(final String balance) throws Exception {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String strInternationalAmount = AppUtility.currencyFormat(balance);
                                availableBalance.setText(getResources().getString(
                                        R.string.availableBalance,
                                        strInternationalAmount));
                            }
                        });

                    }

                    @Override
                    public void failure(Throwable t) {
                        Log.e("get balance t", t.getMessage() + "");
                    }

                    @Override
                    public void failure(String message) {
                        Log.e("get balance msg", message);
                    }
                });
    }

    @Override
    public void onGetUSDValueOfXDCFailure(String failure) {
        Log.e("currency send ", failure);
    }

    @Override
    public void onGetUSDValueOfXDCSuccess(double USDValue) {
        updateBalance(USDValue);
    }

    private void updateBalance(double USDValue) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (bal_str != null && Validations.hasText(bal_str)) {
                    if (USDValue > 0) {
                        tv_usd.setText(getString(R.string.txt_dollar)
                                + String.format(STRING_FORMAT, Double.parseDouble(bal_str) * USDValue)
                                + TEXT_USD);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SendActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
