package com.example.personaldev.policeonduty.dashboard.heat_map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.personaldev.policeonduty.R;

public class Heat_Map extends AppCompatActivity {
    WebSettings webSettings;
    WebView webView;
    String HeatMapURL = "https://auxiliarsweb.web.app/heatmap";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_heat__map);


        loadWebView();
    }

    private void loadWebView(){

        webView = (WebView) findViewById(R.id.webView_heatMap);
        webView.setWebViewClient(new WebViewClient());
        webSettings = (WebSettings) webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(HeatMapURL);
    }
}
