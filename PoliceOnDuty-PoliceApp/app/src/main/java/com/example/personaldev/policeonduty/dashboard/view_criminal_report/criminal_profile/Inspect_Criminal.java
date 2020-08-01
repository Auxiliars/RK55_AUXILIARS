package com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminal_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.heat_map.Heat_Map;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.CriminalModel;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.Criminal_detailed_view;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminal_profile.track_criminal.TrackCriminal;
import com.google.android.material.textfield.TextInputEditText;

public class Inspect_Criminal extends AppCompatActivity implements View.OnClickListener {

    Button icr_btn_done, icr_btn_hle, icr_btn_call, icr_btn_verify_video, icr_btn_verify_fingerprint;
    Button btn_done;
    TextInputEditText lat_text,longi_text;
    String lat,longi,ID;

    ImageView backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inspect__criminal);

        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inspect_Criminal.this, Criminal_detailed_view.class));
            }
        });

        btn_done = findViewById(R.id.icr_btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inspect_Criminal.this, "Verification Done", Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(Inspect_Criminal.this, Dashboard.class));

            }
        });

        icr_btn_verify_video = (Button) findViewById(R.id.icr_verify_video);
        icr_btn_verify_fingerprint = (Button) findViewById(R.id.icr_verify_fingerprint);
        //icr_btn_done = (Button) findViewById(R.id.icr_btn_done);
        icr_btn_hle = (Button) findViewById(R.id.icr_btn_hle);
        icr_btn_call = (Button) findViewById(R.id.icr_btn_Call);

        icr_btn_verify_video.setOnClickListener(this);
        icr_btn_verify_fingerprint.setOnClickListener(this);
        //icr_btn_done.setOnClickListener(this);
        icr_btn_hle.setOnClickListener(this);
        icr_btn_call.setOnClickListener(this);
        lat_text = findViewById(R.id.textInputEditText_Lat);
        longi_text = findViewById(R.id.textInputEditText_Longi);

        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            ID = intent.getStringExtra("ID");
            lat = intent.getStringExtra("lat");
            longi = intent.getStringExtra("longi");

            lat_text.setText(lat);
            longi_text.setText(longi);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*case R.id.icr_btn_done:
                Toast.makeText(this, "Periodic Inspection Done", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.icr_btn_hle:
                Toast.makeText(this, "Sent for Higher Level of Enquiry", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icr_btn_Call:
                Toast.makeText(this, "Calling Criminal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icr_verify_fingerprint:
                Toast.makeText(this, "Fingerprint Verified", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icr_verify_video:
                Toast.makeText(this, "Video Authenticated", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void track_Criminal(View view){
        Intent intent0 = new Intent(this, TrackCriminal.class).putExtra("ID",ID)
                .putExtra("lat",lat)
                .putExtra("longi",longi);
        startActivity(intent0);
    }
}