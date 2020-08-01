package com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminal_profile.track_criminal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.personaldev.policeonduty.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TrackCriminal extends AppCompatActivity implements OnMapReadyCallback {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    GoogleMap mGoogleMap;
    String latS, lngS;
    PolylineOptions polylineOptions = new PolylineOptions();

    LatLng IN1;
    double[] latlng = new double[2];
    List<double[]> latLngs = new ArrayList<>();

    //fro Date Picker
    TextView mTv1, mTv2;
    Button mBtn1, mBtn2;

    Calendar c1,c2;
    DatePickerDialog dpd1, dpd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_track_criminal);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);

        /* START : code for Date Picker */

        String date_n = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        //DatePicker FROM
        mTv1 = findViewById(R.id.from_date_textView);
        mTv1.setText(date_n);
        mBtn1 = findViewById(R.id.from_date);

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c1 = Calendar.getInstance();
                int day = c1.get(Calendar.DAY_OF_MONTH);
                int month = c1.get(Calendar.MONTH);
                int year = c1.get(Calendar.YEAR);

                dpd1 = new DatePickerDialog(TrackCriminal.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mTv1.setText(dayOfMonth + "/" + (month+1) +"/" + year);

                    }
                },day, month, year);
                dpd1.show();

            }
        });

        //DatePicker TO
        mTv2 = findViewById(R.id.to_date_textView);
        mTv2.setText(date_n);
        mBtn2 = findViewById(R.id.to_date);

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c2 = Calendar.getInstance();
                int day = c2.get(Calendar.DAY_OF_MONTH);
                int month = c2.get(Calendar.MONTH);
                int year = c2.get(Calendar.YEAR);

                dpd2 = new DatePickerDialog(TrackCriminal.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mTv2.setText(dayOfMonth + "/" + (month+1) +"/" + year);

                    }
                },day, month, year);
                dpd2.show();

            }
        });

        /* END: code for Date Picker */

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        mGoogleMap = googleMap;
        polylineOptions.width(12);
        polylineOptions.clickable(true);
        polylineOptions.color(Color.RED);
//        polylineOptions.add(new LatLng(38,-123));
//        Polyline polyline1 = googleMap.addPolyline(polylineOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.106144, 78.871732), 4));


        //Firestore Rerieval
        CollectionReference cr = fs.collection("CriminalName/Locations/LocationHistory");
        cr.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (QueryDocumentSnapshot queryDocumentSnapshot : value) {
                    latS = queryDocumentSnapshot.get("Latitude").toString().trim();
                    lngS = queryDocumentSnapshot.get("Longitude").toString().trim();
                    double lat = Double.parseDouble(latS);
                    double ln = Double.parseDouble(lngS);
                    addPoint(lat, ln);
                    latlng[0] = lat;
                    latlng[1] = ln;

                    latLngs.add(latlng);
                    Log.i("BRET", "Size : " + Integer.toString(latLngs.size()) + Double.toString(latLngs.get(latLngs.size() - 1)[0]) + " , " + Double.toString(latlng[1]));
                }
//                Log.i("BRET","OPOP" + Double.toString(latLngs.get(3)[0]) );
                initPolyline(googleMap);
            }
        });


    }

    public void initPolyline(GoogleMap googleMap) {
//        Log.i("BRET","papapap" + Integer.toString(latLngs.size()) + "    " + Double.toString(latLngs.get(3)[0]));
//        for(int i=0 ; i<latLngs.size();i++){
//            Log.i("BRET",Double.toString(latLngs.get(i)[0]) + " , " + Double.toString(latLngs.get(i)[1]));
//            polylineOptions.add(new LatLng(latLngs.get(i)[0],latLngs.get(i)[1]));
//        }
        mGoogleMap.addPolyline(polylineOptions);
    }

    public void addPoint(double lat, double longi) {
        polylineOptions.add(new LatLng(lat, longi));
    }
}