package com.app.xdcpay.Activities.SecurityPrivacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextView;
import com.app.xdcpay.Views.TextViewMedium;

public class ShowPrivateKeyActivity extends BaseActivity {
    private TextViewMedium tv_copyClip, title;
    private TextView tvPrivateKey;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_private_key);

    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        tv_copyClip = findViewById(R.id.tv_copyClip);
        tvPrivateKey = findViewById(R.id.tvPrivateKey);
        setData();
    }

    @Override
    public void setListener() {
        tv_copyClip.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void setData() {

        title.setText(getString(R.string.show_private_key));
        tvPrivateKey.setText(getselectedaccount().accountPrivateKey);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_copyClip:
                if (!tv_copyClip.getText().toString().isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Private Key", tv_copyClip.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), getString(R.string.copy_to_clipboard), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_private_key), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}