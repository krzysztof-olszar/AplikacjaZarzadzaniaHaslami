package com.example.zarzadzaniehaslami;
//Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

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
                Intent intent = new Intent(this, MainMenu.class);
                startActivity(intent);
            }
        }catch (Exception e) { }
    }

}