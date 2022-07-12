package com.example.wifilocation997.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.wifilocation997.R;

public class HtmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        @SuppressLint("WrongViewCast") WebView webView = findViewById(R.id.webview_policy);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        switch (type){
            case "privacy":
                webView.loadUrl("file:///android_asset/privacy_policy.html");
                break;
            case "sdk":
                webView.loadUrl("file:///android_asset/sdk.html");
                break;
            case "about":
                webView.loadUrl("https://baijiahao.baidu.com/s?id=1705269130511313344&wfr=spider&for=pc");
                break;
        }


    }
}