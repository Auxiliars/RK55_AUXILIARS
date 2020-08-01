package com.example.personaldev.criminialsurveillanceapp.login_otp;

public class Validation {

    public static String validate(User user){
        if(user.getuName().equals("admin") && user.getuPass().equals("admin")){
            return "success";
        }
        else{
            return "fails";
        }
    }
}
