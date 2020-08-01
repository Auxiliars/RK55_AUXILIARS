package com.example.personaldev.policeonduty.dashboard.add_criminal_report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.add_criminal_report.acr_fragment.acr_crime_subject;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.Criminal_detailed_view;

public class Add_Criminal_Report extends AppCompatActivity {

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    Button btn_cpd_nxt_fragment;
    ImageView backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add__criminal__report);

        contextOfApplication = getApplicationContext();

        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_Criminal_Report.this, Dashboard.class));
            }
        });

        btn_cpd_nxt_fragment = findViewById(R.id.acr_cpd_btn_next_frag);

        btn_cpd_nxt_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.acr_main_holder, new acr_crime_subject()).commit();

            }
        });

    }
}
