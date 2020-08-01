package com.example.personaldev.policeonduty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.personaldev.policeonduty.dashboard.main.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    Button callDasboard;
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

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_o_t_p);

        Intent intent = getIntent();

         firstName = intent.getStringExtra("FirstName");
         lastName = intent.getStringExtra("LastName");
         userName = intent.getStringExtra("UserName");
         email = intent.getStringExtra("Email");
         password = intent.getStringExtra("Password");
        // confirmPassword = intent.getStringExtra("ConfirmPassword");
         phoneNumber = intent.getStringExtra("PhoneNumber");

        mDatabase = FirebaseDatabase.getInstance().getReference("Account");

        callDasboard = (Button) findViewById(R.id.verifyOTP);
        textElement = findViewById(R.id.prePhoneNoText);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        userCodeNumber = findViewById(R.id.pinViewBox);

        textElement.setText(textElement.getText().toString()+" \""+phoneNumber+" \"");

        sendVerificationCodeToUser(phoneNumber);

        callDasboard.setOnClickListener(new View.OnClickListener() {
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
    }


    public void sendVerificationCodeToUser(String phoneNumber){

        //PhoneNumber Authentication
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+1"+ phoneNumber,                // Phone number to verify
                60,                         // Timeout duration
                TimeUnit.SECONDS,           // Unit of timeout
                VerifyOTP.this,  // Activity (for callback binding)
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
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String verificationCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,verificationCode);
        signInByCredential(credential);

    }

    private void signInByCredential(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            //Saving user Detail to Database
                            mDatabase.child(userName.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("FirstName").setValue(firstName.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("LastName").setValue(lastName.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("Email").setValue(email.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("Password").setValue(password.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("Id").setValue(1);

                            //mDatabase.child(userName.toString().trim()).child("ConfirmPassword").setValue(confirmPassword.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("PhoneNumber").setValue("+1"+phoneNumber.toString().trim());

                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
                            Toast.makeText(VerifyOTP.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}


