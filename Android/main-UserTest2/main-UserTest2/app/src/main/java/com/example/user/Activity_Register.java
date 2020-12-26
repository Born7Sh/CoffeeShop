package com.example.user;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.appcompat.app.AppCompatActivity;


public class Activity_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        WebView register = (WebView) findViewById(R.id.Page_Register);
        WebSettings registerSettings = register.getSettings();
        register.setWebViewClient(new WebViewClient());
        registerSettings.setJavaScriptEnabled(true);
        registerSettings.setSupportMultipleWindows(true);
        registerSettings.setUseWideViewPort(true);
        registerSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        register.loadUrl("http://13.125.237.247:8000/accounts/register");


    }
}

