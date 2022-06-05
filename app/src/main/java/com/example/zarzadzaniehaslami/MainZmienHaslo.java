package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainZmienHaslo extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmienhaslo);
        File spr = new File(MainZmienHaslo.this.getFilesDir(),"dane/ustawienia.txt");
        try {
            FileReader fileReader = new FileReader(spr);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if(bufferedReader.readLine().equals("1")){
                CheckBox czek = findViewById(R.id.czek);
                czek.setChecked(true);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void zmienHaslo(View view){
        TextView stareHasloTV = findViewById(R.id.EditPass1);
        TextView hasloTV = findViewById(R.id.EditPass2);
        TextView potwierdzHasloTV = findViewById(R.id.EditPass3);
        String stareHaslo = stareHasloTV.getText().toString();
        String haslo = hasloTV.getText().toString();
        String potwierdzHaslo = potwierdzHasloTV.getText().toString();
        if(!haslo.equals(potwierdzHaslo)){
            Toast.makeText(MainZmienHaslo.this, "Hasła się nie zgadzają", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(MainZmienHaslo.this.getFilesDir(), "dane/MainHaslo.txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String stareHasloWPliku = bufferedReader.readLine();

            if(!Szyfrowanie.SHA256(stareHaslo).equals(stareHasloWPliku)){
                Toast.makeText(MainZmienHaslo.this, "Złe stare hasło", Toast.LENGTH_SHORT).show();
                return;
            }
            FileWriter writer = new FileWriter(file);
            writer.write(Szyfrowanie.SHA256(haslo));
            writer.flush();
            writer.close();

            String OldKey = Szyfrowanie.SECRET_KEY;
            Szyfrowanie.SECRET_KEY = haslo;
            //tutaj wszystko convertuj
            File path = new File(MainZmienHaslo.this.getFilesDir(), "dane/Baza");
            if (!path.exists()) {
                path.mkdir();
            }
            String[] listaPlikow = path.list();
            for(int i=0;i<listaPlikow.length;i++){
                File dane = new File(path,listaPlikow[i]);

                CzytajPlik czytajPlik = new CzytajPlik(dane);
                czytajPlik.konwertuj(OldKey);
            }
            File dane = new File(MainZmienHaslo.this.getFilesDir(),"dane/Archiwum.txt");

            CzytajPlikArchiwum czytajPlikArchiwum = new CzytajPlikArchiwum(dane);
            czytajPlikArchiwum.konwertuj(OldKey);
        }catch (Exception e) { }
        Toast.makeText(MainZmienHaslo.this, "Pomyślnie zmieniono hasło aplikacji", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void dwuetap(View view){
        File file = new File(MainZmienHaslo.this.getFilesDir(), "dane/ustawienia.txt");
        CheckBox checkdwuetap = findViewById(R.id.czek);
        if(checkdwuetap.isChecked()) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(file,false);
                writer.write("1");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            FileWriter writer = null;
            try {
                writer = new FileWriter(file,false);
                writer.write("0");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
