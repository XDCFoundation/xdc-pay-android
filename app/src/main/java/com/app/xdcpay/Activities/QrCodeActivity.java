package com.app.xdcpay.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.xdcpay.DataBase.Entity.AccountEntity;
import com.app.xdcpay.DataBase.Entity.NetworkEntity;
import com.app.xdcpay.DataBase.NetworkDataBase;
import com.app.xdcpay.Pref.ReadPreferences;
import com.app.xdcpay.Pref.ReadWalletDetails;
import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Validations;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static com.app.xdcpay.Utils.Constants.APOTHEM_NAME;
import static com.app.xdcpay.Utils.Constants.LOCALHOST_8545_NAME;
import static com.app.xdcpay.Utils.Constants.MAIN_NET_NAME;

public class QrCodeActivity extends BaseActivity {
    private ImageView qr_code_iv;
    private TextView address, account_name, network_name;
    private List<NetworkEntity> networkLists = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    ReadPreferences readAutoLockTimerPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
    }

    @Override
    public void getId() {
        qr_code_iv = findViewById(R.id.qr_code_iv);
        address = findViewById(R.id.wallet_address);
        account_name = findViewById(R.id.account_name);
        network_name = findViewById(R.id.network_name);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.copy_to_clipboard).setOnClickListener(this);
    }

    @Override
    public void setData() {
        readAutoLockTimerPref = new ReadPreferences(QrCodeActivity.this);
        networkLists = NetworkDataBase.getInstance(getApplicationContext()).getDatabaseDao().getNetworkList();
        list.add(MAIN_NET_NAME);
        list.add(APOTHEM_NAME);
        list.add(LOCALHOST_8545_NAME);
        if (networkLists.size() > 0) {
            for (int i = 0; i < networkLists.size(); i++) {
                list.add(networkLists.get(i).getNetworkName());
            }
        }
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).equals(readAutoLockTimerPref.getNetworkName())) {
                setAccount(QrCodeActivity.this, getselectedaccount().getId(), null);
                network_name.setText(readAutoLockTimerPref.getNetworkName());
            }
        }
    }

    public void setAccount(Context context, int id, BottomSheetDialog bottomSheetDialog) {
        List<AccountEntity> accountlist = NetworkDataBase.getInstance(context).getAccountDao().getAccountList();
        for (int i = 0; i < accountlist.size(); i++) {
            if (id == accountlist.get(i).id) {
                AccountEntity account = accountlist.get(i);
                account_name.setText(account.accountName);
                String add = account.accountAddress;
                if (add.startsWith("0x"))
                    add = add.replaceFirst("0x", "xdc");
                address.setText(add);

                if (Validations.hasText(account.accountAddress)) {

                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    QRGEncoder qrgEncoder = new QRGEncoder(account.accountAddress, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qr_code_iv.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("qr_code_excep", e.toString());
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.copy_to_clipboard:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("wallet Address", address.getText().toString());
                clipboard.setPrimaryClip(clip);
                showtoast(getResources().getString(R.string.copied));
                break;

        }
    }
}
