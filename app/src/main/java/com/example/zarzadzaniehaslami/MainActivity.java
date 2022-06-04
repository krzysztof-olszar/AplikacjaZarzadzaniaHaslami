package com.example.zarzadzaniehaslami;
//Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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



public class MainActivity extends AppCompatActivity {

    int finger = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(MainActivity.this.getFilesDir(), "dane");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File MainHaslo = new File(file, "MainHaslo.txt");
            if(!MainHaslo.exists()) {
                Intent intent = new Intent(this, MainHaslo.class);
                startActivity(intent);
            }
        } catch (Exception e) { }
        /*Log.println(Log.INFO,"score", Integer.toString(Hasla.silaHasla("AniaAA")));
        Log.println(Log.INFO,"score", Integer.toString(Hasla.silaHasla("Adam-Sandler2137")));
        Log.println(Log.INFO,"score", Hasla.generujHaslo(20,true,true,true));*/
    }

    public Boolean checkBiometricSupport() {

        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        PackageManager packageManager = this.getPackageManager();

        if (!keyguardManager.isKeyguardSecure()) {
            Toast.makeText(MainActivity.this, "Blokada ekranu nie zostala wlaczona", Toast.LENGTH_LONG).show();
            return false;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(MainActivity.this, "Logowanie poprzez odciska palca nie zostala wlaczona", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MainActivity.this, "Blad logowania: " + errString, Toast.LENGTH_LONG).show();
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
                Toast.makeText(MainActivity.this, "Logowanie udana", Toast.LENGTH_LONG).show();
                super.onAuthenticationSucceeded(result);
                finger = 1;
                Przejdz();


            }
        };
    }

    private CancellationSignal cancellationSignal;

    private CancellationSignal getCancellationSignal(){
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){
            @Override
            public void onCancel(){
                Toast.makeText(MainActivity.this, "Sygnal odmowy", Toast.LENGTH_LONG).show();
            }
        });
        return cancellationSignal;
    }

    public void authenticateUser(){
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Biometric Demo")
                .setSubtitle("Autentykacja aby kontynuowac")
                .setDescription("Logowanie za pomoca biometri")
                .setNegativeButton("Cancel", this.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Odmowa logowania", Toast.LENGTH_LONG).show();
                    }
                })
                .build();
        biometricPrompt.authenticate(getCancellationSignal(), getMainExecutor(), getAuthenticationCallback());
    }

    public void Zaloguj(View view){
        TextView hasloOkno = (TextView)findViewById(R.id.MainPassword);
        String wpisaneHaslo=hasloOkno.getText().toString();
        File file = new File(MainActivity.this.getFilesDir(), "dane/MainHaslo.txt");
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String haslo = bufferedReader.readLine();
            if(Szyfrowanie.SHA256(wpisaneHaslo).equals(haslo)){
                Szyfrowanie.SECRET_KEY = wpisaneHaslo;

                File file1 = new File(MainActivity.this.getFilesDir(), "dane/ustawienia.txt");
                try{
                    if(!file1.exists()) {
                        FileWriter writer = null;
                        try {
                            writer = new FileWriter(file1,false);
                            writer.write("0");
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    FileReader fileReader1 = new FileReader(file1);
                    BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                    if(bufferedReader1.readLine().equals("1")){
                        checkBiometricSupport();
                        authenticateUser();
                        //if(finger == 1){
                         //   finger = 0;
                         //   Intent intent = new Intent(this, MainMenu.class);
                         //   startActivity(intent);
                        //}

                    }
                    else{
                        Intent intent = new Intent(this, MainMenu.class);
                        startActivity(intent);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e) { }
    }

    public void Przejdz(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


}