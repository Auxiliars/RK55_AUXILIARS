package com.example.personaldev.policeonduty.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.login.Login;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    Button callLogIn, gotoOTPbtn;
    TextInputLayout FirstName;
    TextInputLayout LastName;
    TextInputLayout UserName;
    TextInputLayout Email;
    TextInputLayout PhoneNumber;
    TextInputLayout Password;
    TextInputLayout ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        //taking values from signUp form
        FirstName = findViewById(R.id.firstName_s);
        LastName = findViewById(R.id.lastName_s);
        UserName = findViewById(R.id.username_s);
        Email = findViewById(R.id.email_s);
        PhoneNumber =  findViewById(R.id.phoneNo_s);
        Password = findViewById(R.id.password_s);
        ConfirmPassword = findViewById(R.id.confirm_password_s);

        callLogIn =(Button) findViewById(R.id.login_screen);
        callLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginScreen();
            }
        });

        gotoOTPbtn = (Button) findViewById(R.id.signup_go_btn);
        gotoOTPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openOTPScreen();
            }
        });
    }
    public void openLoginScreen() {
        Intent intent1 = new Intent(this, Login.class);
        startActivity(intent1);
    }

    public void openOTPScreen(){
        String firstName   = FirstName.getEditText().getText().toString().trim();
        String lastName    = LastName.getEditText().getText().toString().trim();
        String userName    = UserName.getEditText().getText().toString().trim();
        String email = Email.getEditText().getText().toString().trim();
        String phoneNumber = PhoneNumber.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();
        String confirmPassword = ConfirmPassword.getEditText().getText().toString().trim();

        if(password.equals(confirmPassword)){
            Intent intent2 = new Intent(getApplicationContext(), VerifyOTP.class);


            intent2.putExtra("FirstName",firstName);
            intent2.putExtra("LastName",lastName);
            intent2.putExtra("UserName",userName);
            intent2.putExtra("Email",email);
            intent2.putExtra("Password",password);
            //intent2.putExtra("ConfirmPassword",confirmPassword);
            intent2.putExtra("PhoneNumber",phoneNumber);
            startActivity(intent2);
        }
        else{
            Toast.makeText(Signup.this,"Password and Confirm Password is not Same", Toast.LENGTH_SHORT).show();
        }


    }
}
