package com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.background_Verfication;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.personaldev.criminialsurveillanceapp.R;

public class videoPlayActivity extends AppCompatActivity {

    private VideoView bgv_videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        bgv_videoView = findViewById(R.id.bgv_videoView);

//        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
//        bgv_videoView.setVideoURI(videoUri);
//        bgv_videoView.start();

    }
}