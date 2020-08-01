package com.example.personaldev.criminialsurveillanceapp.Authentication_Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.Executor;

import com.example.personaldev.criminialsurveillanceapp.R;

public class BioMetric extends AppCompatActivity {
    TextView tvmesg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_metric);

        tvmesg = findViewById(R.id.tvMessage);
        if (Build.VERSION.SDK_INT >= 28){
            BiometricManager biometricManager = BiometricManager.from(this);

            switch (biometricManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    tvmesg.setText("You can use the fingerprint sensor for Authentication");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    tvmesg.setText("The device doesn't have a fingerprint sensor");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    tvmesg.setText("The biometric sensor is currently unavailable");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    tvmesg.setText("Your device doesn't have any fingerprint saved,please check your security setting");
                    break;

            }
            Executor executor = ContextCompat.getMainExecutor(this);
            BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(BioMetric.this, "Error", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(BioMetric.this, "Authentication success!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BioMetric.this,Authentication_Dashboard.class));
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(BioMetric.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });

            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Use fingerprint sensor to authenticate yourself")
                    .setNegativeButtonText("cancel")
                    .build();

            biometricPrompt.authenticate(promptInfo);
        }
        else {
            Toast.makeText(this,"You Dont Have Minimum Requirements For This Type Of Verification",Toast.LENGTH_LONG).show();
        }

    }
}