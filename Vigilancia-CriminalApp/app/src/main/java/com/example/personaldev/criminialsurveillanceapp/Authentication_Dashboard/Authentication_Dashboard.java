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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.background_Verfication.bg_verification;
import com.example.personaldev.criminialsurveillanceapp.R;
import com.example.personaldev.criminialsurveillanceapp.login_otp.Login_Screen;
import com.example.personaldev.criminialsurveillanceapp.login_otp.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Authentication_Dashboard extends AppCompatActivity {

    private TextView BioMetricActivity;
    private TextView bg_verification;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");

    //For Notification
    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
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

        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

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

    private void sendNotification(){
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","Verification");
            notificationObj.put("body", "Please do Video Verification");

            mainObj.put("notification",notificationObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //code here will run
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //code here will run on error
                        }
                    }

            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAPI5KTFc:APA91bHo3GyobQX3Zpo3n5G49YWDLdGWStXrP8uWQfYiOF0dEAzV69CyCQ_6VwBVyTvOsCCsAC3dVCVBwIQm8dnISAN_WRQ63UYOr9XMwSgBCOIpqyzoyQjl8FFuxQUuv9gw1WLGHklk");
                    return header;
                }
            };

            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}