package com.example.personaldev.policeonduty.dashboard.heat_map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;

public class Heat_Map extends AppCompatActivity {
    WebSettings webSettings;
    WebView webView;
    String HeatMapURL = "https://auxiliarsweb.web.app/heatmap";

    ImageView backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_heat__map);


        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Heat_Map.this, Dashboard.class));
            }
        });

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
