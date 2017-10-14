package com.example.changshuai.microuniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TestActivity extends AppCompatActivity {
    private WebView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        test = (WebView) findViewById(R.id.test);
        test.getSettings().setJavaScriptEnabled(true);
        test.getSettings().setBuiltInZoomControls(false);
        test.setWebViewClient(new WebViewClient());
        test.loadUrl("http://59.48.197.202:50000/_data/home_login.aspx");
    }

    private class WebViewClient extends android.webkit.WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            CookieManager cookieManager = CookieManager.getInstance();
            String CookieStr = cookieManager.getCookie(url);
            Log.e("sunzn", "Cookies = " + CookieStr);
        }
    }
}
