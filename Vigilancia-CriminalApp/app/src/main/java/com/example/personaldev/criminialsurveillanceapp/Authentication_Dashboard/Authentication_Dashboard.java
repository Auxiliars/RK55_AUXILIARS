package com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.background_Verfication.bg_verification;
import com.example.personaldev.criminialsurveillanceapp.R;
import com.example.personaldev.criminialsurveillanceapp.login_otp.Login_Screen;
import com.example.personaldev.criminialsurveillanceapp.login_otp.User;

public class Authentication_Dashboard extends AppCompatActivity {

    private TextView BioMetricActivity;
    private TextView bg_verification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authentication_dashboard);

        BioMetricActivity = findViewById(R.id.db1_text);
        BioMetricActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Authentication_Dashboard.this, BioMetric.class));
            }
        });

        bg_verification = findViewById(R.id.db2_text);
        bg_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Authentication_Dashboard.this, com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.background_Verfication.bg_verification.class));
            }
        });
    }

    public void logout(View view) {

        new User(Authentication_Dashboard.this).removeUser();
        startActivity(new Intent(Authentication_Dashboard.this, Login_Screen.class));
        finish();

    }
}