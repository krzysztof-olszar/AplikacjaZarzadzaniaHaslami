package com.example.zarzadzaniehaslami;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import android.view.View;
import android.content.DialogInterface;
import android.os.CancellationSignal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;

import android.app.KeyguardManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.hardware.biometrics.BiometricPrompt;

public class BiometricDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_demo);

        checkBiometricSupport();
    }



    private Boolean checkBiometricSupport() {

        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        PackageManager packageManager = this.getPackageManager();

        if (!keyguardManager.isKeyguardSecure()) {
            Toast.makeText(BiometricDemoActivity.this, "Blokada ekranu nie zostala wlaczona", Toast.LENGTH_LONG).show();
            return false;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(BiometricDemoActivity.this, "Logowanie poprzez odciska palca nie zostala wlaczona", Toast.LENGTH_LONG).show();
            return false;
        }

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            return true;
        }

        return true;
    }

    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {

        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                Toast.makeText(BiometricDemoActivity.this, "Blad logowania: " + errString, Toast.LENGTH_LONG).show();
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                Toast.makeText(BiometricDemoActivity.this, "Logowanie udana", Toast.LENGTH_LONG).show();
                super.onAuthenticationSucceeded(result);
            }
        };
    }

    private CancellationSignal cancellationSignal;

    private CancellationSignal getCancellationSignal(){
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){
            @Override
            public void onCancel(){
                Toast.makeText(BiometricDemoActivity.this, "Sygnal odmowy", Toast.LENGTH_LONG).show();
            }
        });
        return cancellationSignal;
    }

    public void authenticateUser(View view){
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Biometric Demo")
                .setSubtitle("Autentykacja aby kontynuowac")
                .setDescription("Logowanie za pomoca biometri")
                .setNegativeButton("Cancel", this.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(BiometricDemoActivity.this, "Odmowa logowania", Toast.LENGTH_LONG).show();
                    }
                })
                .build();
        biometricPrompt.authenticate(getCancellationSignal(), getMainExecutor(), getAuthenticationCallback());
    }


}