package com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

        Toast.makeText(this, getUniqueIMEIId(this),Toast.LENGTH_LONG);
//        String android_id = Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        Log.i("BRET",android_id);



        Log.i("BRET", "REACHED");

        Log.i("BRET", getUniqueIMEIId(this));
    }

    public void logout(View view) {

        new User(Authentication_Dashboard.this).removeUser();
        startActivity(new Intent(Authentication_Dashboard.this, Login_Screen.class));
        finish();

    }


    public String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.READ_PHONE_STATE},123);
            }
            String imei = telephonyManager.getDeviceId();

//            String imei = telephonyManager.getImei(1);
            Log.e("BRET", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            String imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            return imei;

        }
    }
}