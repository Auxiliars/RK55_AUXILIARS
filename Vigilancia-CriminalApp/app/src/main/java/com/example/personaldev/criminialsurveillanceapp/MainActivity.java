package com.example.personaldev.criminialsurveillanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.Authentication_Dashboard;
import com.example.personaldev.criminialsurveillanceapp.login_otp.Login_Screen;
import com.example.personaldev.criminialsurveillanceapp.login_otp.User;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(new User(MainActivity.this).getuName() != "") {
                    startActivity(new Intent(MainActivity.this, Authentication_Dashboard.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Login_Screen.class));
                }
                finish();

            }
        }, 2000);

    }

}
