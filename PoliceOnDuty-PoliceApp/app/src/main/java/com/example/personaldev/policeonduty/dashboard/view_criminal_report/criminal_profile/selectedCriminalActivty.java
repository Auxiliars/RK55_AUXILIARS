package com.example.personaldev.policeonduty.dashboard.view_criminal_report.criminal_profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.heat_map.Heat_Map;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.example.personaldev.policeonduty.dashboard.toolbar.Toolbar_notification;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.CriminalModel;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.Criminal_detailed_view;
import com.example.personaldev.policeonduty.dashboard.view_criminal_report.Names;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class selectedCriminalActivty extends AppCompatActivity {

    TextInputLayout a,b,c,d,e,f,g,h,i;
    TextInputEditText email_layout,j;
    TextView tvCriminal,id_layout;
    ImageView backActivity;
    Button sc_btn_inspect;
    String ID;
    
    private FirebaseDatabase mdataBase;
    private DatabaseReference mdataReference;
    public String name="NA", city="NA",state="NA",zipcode="NA",mainAdress="NA",report="NA",age="NA",email="NA",phone="NA",authentication = "true",cLat="NA",cLong="NA",dob="NA",gender="NA";
    public String[] location = new String[2];
    public double lat,longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_selected_criminal_activty);

        backActivity = findViewById(R.id.general_back_activity);
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selectedCriminalActivty.this, Criminal_detailed_view.class));
            }
        });

        sc_btn_inspect = findViewById(R.id.sc_btn_inspect_criminal);
        sc_btn_inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInspectActivity();
            }
        });

        tvCriminal = findViewById(R.id.selectedCriminal);
        a = findViewById(R.id.criminal_age);
        b = findViewById(R.id.criminal_dob);
        c = findViewById(R.id.criminal_Gender);
        d = findViewById(R.id.loc_latitude);
        e = findViewById(R.id.loc_longitude);
        f = findViewById(R.id.criminal_Address);
        g = findViewById(R.id.criminal_ZipCode);
        h = findViewById(R.id.criminal_city);
        i = findViewById(R.id.criminal_state);
        j = findViewById(R.id.subject);
        email_layout = findViewById(R.id.criminal_email);
        id_layout = findViewById(R.id.criminal_Id_dis);

        //criminal report id nhi diya hai
        //j = findViewById(R.id.criminal_report);


        Intent intent = getIntent();

        if(intent.getExtras()!= null){
            CriminalModel criminalModel = (CriminalModel) intent.getSerializableExtra("data");
            Log.i("DRET",criminalModel.Id);
            tvCriminal.setText(criminalModel.getCriminalName());
            ID = criminalModel.Id;
            //Getting Data From Firebase And Storing in local Variables
            mdataBase = FirebaseDatabase.getInstance();
            mdataReference = mdataBase.getReference();

            mdataReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("BRET","3rd Base");
                    try {
                        DataSnapshot ds = snapshot.child("criminals").child(ID);

                            Log.i("BRET",ds.child("name").getValue().toString());
                            name = ds.child("name").getValue().toString();
                            report = ds.child("report").getValue().toString();
                            age = ds.child("age").getValue().toString();
                            phone = ds.child("phone").getValue().toString();
                            authentication = ds.child("authentication").getValue().toString();
                            cLat = ds.child("location").child("l01").getValue().toString();
                            cLong = ds.child("location").child("l02").getValue().toString();
                            dob = ds.child("dob").getValue().toString();
                            gender = ds.child("gender").getValue().toString();


                            Log.i("TRET",cLat + cLat + phone +age+gender);


                            //GEOCODING//
                            lat= Double.parseDouble(cLat);
                            longi= Double.parseDouble(cLong);
                            String myCity = "";
                            Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
                            try {
                                List<Address>  addresses= geocoder.getFromLocation(lat,longi,1);
                                zipcode = addresses.get(0).getPostalCode();
                                state = addresses.get(0).getAdminArea();
                                city = addresses.get(0).getLocality();
                                mainAdress = addresses.get(0).getAddressLine(0);
                                Log.i("TRET",addresses.toString());
                                Log.i("TRET",mainAdress+" YOYOYOY     " + city + state + zipcode);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            //GEOCODING ENDS//



                        setValues();

                    }
                    catch (Exception e){
                        Log.i("Error is ",e.getMessage());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }



    }
    public void setValues(){
        //Variable Names => id, name,city,state,zipcode,mainAdress,report,age,,authentication = ,cLat,cLong,dob,gender

//        String name= getIntent().getStringExtra("name");
//        String age= getIntent().getStringExtra("age");
//        String dob= getIntent().getStringExtra("dob");
//        String city= getIntent().getStringExtra("city");
//        String state= getIntent().getStringExtra("state");
//        String zipcode= getIntent().getStringExtra("zipcode");
//        String mainAdress= getIntent().getStringExtra("mainAdress");
//        //String report= getIntent().getStringExtra("report");
//        String clat= getIntent().getStringExtra("clat");
//        String clong= getIntent().getStringExtra("clong");
//        String gender= getIntent().getStringExtra("gender");

        tvCriminal.setText(name);
        id_layout.setText(ID);
        a.getEditText().setText(age);
        b.getEditText().setText(dob);
        c.getEditText().setText(gender);
        d.getEditText().setText(cLat);
        e.getEditText().setText(cLong);
        f.getEditText().setText(mainAdress);
        g.getEditText().setText(zipcode);
        h.getEditText().setText(city);
        i.getEditText().setText(state);
        j.setText(report);
        email_layout.setText(email);

    }

    public void openInspectActivity(){
        Intent intent0 = new Intent(this, Inspect_Criminal.class).putExtra("ID",ID)
                .putExtra("lat",cLat)
                .putExtra("longi",cLong);
        startActivity(intent0);
    }
}
