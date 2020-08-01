package com.example.personaldev.policeonduty.signup;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class VerifyOTP extends AppCompatActivity {

    Button callDashboard;
    ImageView imgClose;

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

    //For Crypto
    private String stringMessage;
    private byte encryptionKey[] = {1,3,5,7,9,12,54,-54,90,-65,68,32,-1,46,69,96};
    private Cipher cipher, decipher;
    private SecretKeySpec secretKeySpec;
    //

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_o_t_p);


        //Value getting Intent from SignUp page
        Intent intent = getIntent();

        firstName = intent.getStringExtra("FirstName");
        lastName = intent.getStringExtra("LastName");
        userName = intent.getStringExtra("UserName");
        email = intent.getStringExtra("Email");
        password = intent.getStringExtra("Password");
        // confirmPassword = intent.getStringExtra("ConfirmPassword");
        phoneNumber = intent.getStringExtra("PhoneNumber");

        //

        mDatabase = FirebaseDatabase.getInstance().getReference("Account");

        callDashboard = (Button) findViewById(R.id.verifyOTP);
        textElement = findViewById(R.id.prePhoneNoText);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        userCodeNumber = findViewById(R.id.pinViewBox);

        //For crypto
        try {
            cipher = Cipher.getInstance("AES");
            decipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        //

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
                "+91"+ phoneNumber,                // Phone number to verify
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
                            mDatabase.child(userName.toString().trim()).child("Email").setValue(AESEncryptionMethod(email.toString().trim()));
                            mDatabase.child(userName.toString().trim()).child("Password").setValue(AESEncryptionMethod(password.toString().trim()));
                            //mDatabase.child(userName.toString().trim()).child("ConfirmPassword").setValue(confirmPassword.toString().trim());
                            mDatabase.child(userName.toString().trim()).child("PhoneNumber").setValue("+91"+phoneNumber.toString().trim());
                            
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
                            Toast.makeText(VerifyOTP.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private String AESEncryptionMethod(String string){
        byte[] stringByte = string.getBytes();
        byte[] encryptedByte = new byte[stringByte.length];

        try {
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            encryptedByte = cipher.doFinal(stringByte);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        String returnString=null;

        try {

            returnString = new String(encryptedByte,"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnString;
    }

    private String AESDecryptionMethod(String string) throws UnsupportedEncodingException {
        byte[] EncryptedByte = string.getBytes("ISO-8859-1");
        String decryptedString = string;

        byte[] decryption;
        try {
            decipher.init(cipher.DECRYPT_MODE,secretKeySpec);
            decryption = decipher.doFinal(EncryptedByte);
            decryptedString = new String(decryption);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return decryptedString;
    }
}
