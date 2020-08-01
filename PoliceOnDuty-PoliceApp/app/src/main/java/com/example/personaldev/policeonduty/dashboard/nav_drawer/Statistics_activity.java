package com.example.personaldev.policeonduty.dashboard.nav_drawer;

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

public class Statistics_activity extends AppCompatActivity {

    ImageView backActivity;
    WebView webView;
    WebSettings webSettings;
    String crimeStatsUrl= "https://auxiliarsweb.web.app/crimeStats";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);

        webView = findViewById(R.id.webViewStats);
        webView.setWebViewClient(new WebViewClient());
        webSettings = (WebSettings) webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(crimeStatsUrl);


        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Statistics_activity.this, Dashboard.class));
            }
        });
    }
}
