package com.example.personaldev.policeonduty.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaldev.policeonduty.R;
import com.example.personaldev.policeonduty.signup.Signup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Login extends AppCompatActivity {

    Button callSignUp, login_go_btn;
    ImageView logo_aux;
    TextView logoTextWelcome, sloganText;
    TextInputLayout username, password;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Database
        mDatabase = FirebaseDatabase.getInstance().getReference("Account");

        //Hooks
        callSignUp = (Button) findViewById(R.id.signup_screen);
        logo_aux = findViewById(R.id.logo_image_l);
        logoTextWelcome = findViewById(R.id.logo_text_welcome_l);
        sloganText = findViewById(R.id.slogan_name_l);
        username = findViewById(R.id.username_l);
        password = findViewById(R.id.password_l);
        login_go_btn = (Button) findViewById(R.id.login_go_btn);

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


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View, String>(logo_aux, "trans_logo_aux");
                pairs[1] = new Pair<View, String>(logoTextWelcome, "trans_text_welcome");
                pairs[2] = new Pair<View, String>(sloganText, "trans_text_signin");
                pairs[3] = new Pair<View, String>(username, "trans_username");
                pairs[4] = new Pair<View, String>(password, "trans_password");
                pairs[5] = new Pair<View, String>(login_go_btn, "trans_btn_go");
                pairs[6] = new Pair<View, String>(callSignUp, "trans_login_signup");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        login_go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOTPScreen();
            }
        });

    }
    public void openOTPScreen(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            String userName = username.getEditText().getText().toString().trim();
            String Password = password.getEditText().getText().toString().trim();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(userName.toString().trim())){

                    Toast.makeText(getApplicationContext(),"UserId Matched",Toast.LENGTH_SHORT).show();
                        stringMessage = snapshot.child(userName.toString().trim()).child("Password").getValue().toString().trim();
                    try {
                        if(AESDecryptionMethod(stringMessage).equals(Password.toString().trim())){
                            Toast.makeText(getApplicationContext(),"Password Matched",Toast.LENGTH_SHORT).show();
                            Intent OTPintent = new Intent(Login.this, Verify_OTP_2.class);
                            OTPintent.putExtra("phoneNumber",snapshot.child(userName.toString().trim()).child("PhoneNumber").getValue().toString().trim());
                            startActivity(OTPintent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Ohh Password Not Matched",Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Opps UserId Not Matched",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
