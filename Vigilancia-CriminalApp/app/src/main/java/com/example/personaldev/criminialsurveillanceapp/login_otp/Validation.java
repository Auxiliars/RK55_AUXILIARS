package com.example.personaldev.criminialsurveillanceapp.login_otp;

public class Validation {

    public static String validate(User user){
        if(user.getuName().equals("cr0001") && user.getuPass().equals("1234")){
            return "success";
        }
        else{
            return "fails";
        }
    }
}
