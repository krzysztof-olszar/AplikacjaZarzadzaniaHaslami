package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.os.Bundle;
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
