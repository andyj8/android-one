package com.andy.auth;

import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WebView webView = (WebView) findViewById(R.id.webview_about);
        webView.loadUrl("file:///android_asset/about.html");

    }

}
