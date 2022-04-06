package com.app.xdcpay.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Utils.Constants;

public class BrowserActivity extends BaseActivity {
    private WebView webView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
    }

    @Override
    public void getId() {
        title = findViewById(R.id.title);
        webView = findViewById(R.id.web_view);

        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void setData() {
        title.setText(getIntent().getStringExtra(Constants.TITLE));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
            }
        });

        Log.e("url for obs ", getIntent().getStringExtra(Constants.URL));
        webView.loadUrl(getIntent().getStringExtra(Constants.URL));
    }

    @Override
    public void onClick(View v) {

    }
}
