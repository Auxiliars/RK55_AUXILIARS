package com.example.personaldev.criminialsurveillanceapp.login_otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard.Authentication_Dashboard;
import com.example.personaldev.criminialsurveillanceapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login_Screen extends AppCompatActivity {

    TextInputEditText un, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        un = (TextInputEditText) findViewById(R.id.cs_username);
        pw = (TextInputEditText) findViewById(R.id.cs_password);

    }

    public void login(View view) {
        String userName = un.getText().toString().trim();
        String passWord = pw.getText().toString().trim();

        Toast.makeText(this, userName + " " + passWord, Toast.LENGTH_SHORT).show();

        if (userName.equals("") || passWord.equals("")) {
            Toast.makeText(this, "Please fill All the fields", Toast.LENGTH_SHORT).show();
        } else if (userName.equals("admin") && passWord.equals("admin")) {
            startActivity(new Intent(Login_Screen.this, Authentication_Dashboard.class));
        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }

//        else {
//            User user = new User(Login_Screen.this);
//            user.setuName(userName);
//            user.setuPass(passWord);
//
//            String msg = Validation.validate(user);
//            if (msg.equals("success")) {
//                startActivity(new Intent(Login_Screen.this, Authentication_Dashboard.class));
//            } else {
//                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}