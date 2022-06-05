package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

public class MainDodaj extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj);


        TextView haslo = findViewById(R.id.addhaslo);
        TextView silaHasla = findViewById(R.id.SilaHasla);
        haslo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int Sila = Hasla.silaHasla(s.toString());
                if(Sila>70){
                    silaHasla.setTextColor(Color.parseColor("#00FF00"));
                    silaHasla.setText("Mocne!");
                }else if(Sila>40){
                    silaHasla.setTextColor(Color.parseColor("#FFFF00"));
                    silaHasla.setText("Srednie!");
                }else{
                    silaHasla.setTextColor(Color.parseColor("#FF0000"));
                    silaHasla.setText("Słabe!");
                }
            }
        });
    }

    public void Dodaj(View view){
        TextView nazwa = findViewById(R.id.addnazwa);
        TextView login = findViewById(R.id.addlogin);
        TextView haslo = findViewById(R.id.addhaslo);
        TextView linki = findViewById(R.id.addlink);

        File path = new File(MainDodaj.this.getFilesDir(), "dane/Baza");
        if (!path.exists()) {
            path.mkdir();
        }
        try {
            File file = new File(path, nazwa.getText()+".txt");
            CzytajPlik czytajPlik = new CzytajPlik(file);
            czytajPlik.dodaj(login.getText().toString(), haslo.getText().toString(),linki.getText().toString());
            Toast.makeText(MainDodaj.this, "Pomyślnie dodano!", Toast.LENGTH_LONG).show();

            finish();
        } catch (Exception e) { }
    }

    public void Generuj(View view){
        CheckBox znakiSpecjalne = findViewById(R.id.znakiSpecjalne);
        CheckBox duzeZnaki = findViewById(R.id.duzeZnaki);
        CheckBox liczby = findViewById(R.id.liczby);
        TextView dlugosc = findViewById(R.id.dlugosc);

        TextView haslo = findViewById(R.id.addhaslo);
        int dlugoscHasla;
        try {
            dlugoscHasla = Integer.parseInt(dlugosc.getText().toString());

        }catch (NumberFormatException e){
            dlugoscHasla = 6;
        }
        haslo.setText(Hasla.generujHaslo(dlugoscHasla,duzeZnaki.isChecked(),liczby.isChecked(),znakiSpecjalne.isChecked()));
    }
}
