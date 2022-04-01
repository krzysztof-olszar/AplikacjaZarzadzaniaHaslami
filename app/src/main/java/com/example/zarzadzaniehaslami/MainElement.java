package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainElement extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        Intent intent = this.getIntent();
        String name = intent.getStringExtra("nazwa");
        String path = "dane/Baza/"+name;
        File file = new File(MainElement.this.getFilesDir(), path);

        Log.println(Log.INFO,"element",file.getPath());

        String login = "ERROR";
        String password = "ERROR";
        String linki = "ERROR";

        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            login = bufferedReader.readLine();
            password = bufferedReader.readLine();
            linki = bufferedReader.readLine();
        }catch (Exception e) { }

        Button buttonLogin = findViewById(R.id.Login);
        Button buttonPassword = findViewById(R.id.Password);
        TextView textViewLinki = findViewById(R.id.Linki);

        buttonLogin.setText(login);
        buttonPassword.setText(password);
        textViewLinki.setText(linki);
    }
}
