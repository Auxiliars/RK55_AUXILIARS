package com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.background_Verfication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.personaldev.criminialsurveillanceapp.R;

public class bg_verification extends AppCompatActivity {

    private static int VIDEO_REQUEST = 101;
    // private Uri videoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bg_verification);


    }

    public void captureVideo(View view) {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (videoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(videoIntent, VIDEO_REQUEST);
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == VIDEO_REQUEST && requestCode == RESULT_OK) {
//            videoUri = data.getData();
//            //videoView.setVideoURI(videoUri);
//            Log.i("search",videoUri.toString());
//
//        }
//    }
}