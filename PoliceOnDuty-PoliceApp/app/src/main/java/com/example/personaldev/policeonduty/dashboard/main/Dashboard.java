package com.example.personaldev.policeonduty.dashboard.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.personaldev.policeonduty.dashboard.add_criminal_report.Add_Criminal_Report;
import com.example.personaldev.policeonduty.dashboard.heat_map.Heat_Map;
import com.example.personaldev.policeonduty.dashboard.nav_drawer.SOS_Emergency;
import com.example.personaldev.policeonduty.dashboard.nav_drawer.police_profile;
import com.example.personaldev.policeonduty.dashboard.sos_report.SOS_Report;
import com.example.personaldev.policeonduty.dashboard.nav_drawer.Statistics_activity;
import com.example.personaldev.policeonduty.dashboard.toolbar.Toolbar_notification;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.Criminal_detailed_view;
import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.face_verify;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar1;

    //for Criminal detailed(recycler view)
    Button db1_btn, db2_btn, db3_btn, db4_btn;
    ImageView notification_bell;

    TextView db1_text,db2_text,db3_text,db4_text,db31_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        notification_bell = (ImageView) findViewById(R.id.notification_bell);
        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationActivity();
            }
        });

        db31_text = findViewById(R.id.db31_text);
        db31_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFaceRecognition();
            }
        });

        db1_btn = (Button) findViewById(R.id.db1_btn);
        db1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeatMap();
            }
        });
        db1_text = (TextView) findViewById(R.id.db1_text);
        db1_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHeatMap();
            }
        });

        db2_text = (TextView) findViewById(R.id.db2_text);
        db2_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCriminalReport();
            }
        });

        db3_text = (TextView) findViewById(R.id.db3_text);
        db3_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCriminalRecyclerView();
            }
        });

        db4_btn = (Button) findViewById(R.id.db4_btn);
        db4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSOSreport();
            }
        });
        db4_text = (TextView) findViewById(R.id.db4_text);
        db4_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSOSreport();
            }
        });

        toolbar1 = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar1);

        /*------Hooks-------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        toolbar1 = findViewById(R.id.common_toolbar);

        /*------ToolBar-------*/
        setSupportActionBar(toolbar1);

        /*------Navigation Drawer Menu-------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //when the application launch it should select default the home screen navigation menu
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                break;

            case R.id.nav_statistics:
                Intent intent12 = new Intent (Dashboard.this, Statistics_activity.class);
                startActivity(intent12);
                break;

            case R.id.nav_SOS:
                Intent intent13 = new Intent (Dashboard.this, SOS_Emergency.class);
                startActivity(intent13);
                break;

            case R.id.nav_dbd1_heatMap:
                Intent intent1 = new Intent (Dashboard.this, Heat_Map.class);
                startActivity(intent1);
                break;

            case R.id.nav_dbd2_addCriminalRepo:
                Intent intent2 = new Intent (Dashboard.this, Add_Criminal_Report.class);
                startActivity(intent2);
                break;

            case R.id.nav_dbd3_viewCriminalRepo:
                Intent intent3 = new Intent (Dashboard.this, Criminal_detailed_view.class);
                startActivity(intent3);
                break;

            case R.id.nav_dbd4_SOS_repo:
                Intent intent4 = new Intent (Dashboard.this, SOS_Report.class);
                startActivity(intent4);
                break;
            case R.id.nav_app_my_profile:
                Intent intent_pp = new Intent (Dashboard.this, police_profile.class);
                startActivity(intent_pp);
                break;
        }

        //for closing drawer on clickItem
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openFaceRecognition(){
        Intent intent31 = new Intent(this, face_verify.class);
        startActivity(intent31);
    }

    public void openNotificationActivity(){
        Intent intent0 = new Intent(this, Toolbar_notification.class);
        startActivity(intent0);
    }

    public void openHeatMap(){
        Intent intent1 = new Intent(this, Heat_Map.class);
        startActivity(intent1);
    }

    public void openAddCriminalReport(){
        Intent intent2 = new Intent(this, Add_Criminal_Report.class);
        startActivity(intent2);
    }

    public void openCriminalRecyclerView(){
        Intent intent3 = new Intent(this, Criminal_detailed_view.class);
        startActivity(intent3);
    }

    public void openSOSreport(){
        Intent intent4 = new Intent(this, SOS_Report.class);
        startActivity(intent4);
    }

}

