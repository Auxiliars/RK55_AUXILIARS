package com.example.personaldev.policeonduty.dashboard.sos_report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;

public class SOS_Report extends AppCompatActivity {

    ImageView backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sos__report);

        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SOS_Report.this, Dashboard.class));
            }
        });
    }
}
