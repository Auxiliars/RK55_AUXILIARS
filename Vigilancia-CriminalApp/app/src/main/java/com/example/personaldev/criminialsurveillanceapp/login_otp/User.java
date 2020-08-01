package com.example.personaldev.criminialsurveillanceapp.login_otp;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    
    Context context;
    
    SharedPreferences sharedPreferences;

    public User(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("login_details",Context.MODE_PRIVATE);
        
    }

    public String getuName() {
        uName = sharedPreferences.getString("uName","");
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
        sharedPreferences.edit().putString("Username : ", uName).commit();
    }

    public String getuPass() {
        uPass = sharedPreferences.getString("uPass","");
        return uPass;
    }

    public void setuPass(String uPass) {
        this.uPass = uPass;
        sharedPreferences.edit().putString("Password : ",uPass).commit();
    }

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    private String uName, uPass;



}
