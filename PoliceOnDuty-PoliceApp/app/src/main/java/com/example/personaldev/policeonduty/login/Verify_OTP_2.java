package com.example.personaldev.policeonduty.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.example.personaldev.policeonduty.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_OTP_2 extends AppCompatActivity {


    Button callDashboard;
    ImageView imgClose;


    //Button callDasboard;
    TextView textElement;

    String verificationCodeBySystem;
    ProgressBar progressBar;
    PinView userCodeNumber;

    String firstName;
    String lastName;
    String userName;
    String email;
    String phoneNumber;
    String password;
    // String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify__otp_2);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        callDashboard = (Button) findViewById(R.id.verifyOTP);
        textElement = findViewById(R.id.prePhoneNoText);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        userCodeNumber = findViewById(R.id.pinViewBox);

        textElement.setText(textElement.getText().toString()+" \""+phoneNumber+" \"");

        sendVerificationCodeToUser(phoneNumber);


        //callDashboard = (Button) findViewById(R.id.verifyOTP);

        callDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = userCodeNumber.getText().toString();

                if(code.isEmpty()|| code.length()<6){
                    userCodeNumber.setError("Wrong OTP ...");
                    userCodeNumber.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

        imgClose = (ImageView) findViewById(R.id.close_btn);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginScreen();
            }
        });
    }

    public void openDashboardScreen(){
        Intent intent1 = new Intent(this, Dashboard.class );
        startActivity(intent1);
    }

    public void openLoginScreen(){
        Intent intent2 = new Intent(this, Login.class);
        startActivity(intent2);
    }
    public void sendVerificationCodeToUser(String phoneNumber){

        //PhoneNumber Authentication
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,                // Phone number to verify
                60,                         // Timeout duration
                TimeUnit.SECONDS,           // Unit of timeout
                Verify_OTP_2.this,  // Activity (for callback binding)
                mCallbacks);                // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Verify_OTP_2.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String verificationCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,verificationCode);
        signInByCredential(credential);

    }

    private void signInByCredential(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Verify_OTP_2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
                            Toast.makeText(Verify_OTP_2.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
