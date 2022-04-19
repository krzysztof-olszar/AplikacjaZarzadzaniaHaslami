package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainDodajKategorie extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_kategorie);
    }

    public void Dodaj(View view){
        TextView nazwa = findViewById(R.id.addnazwa);

        File path = new File(MainDodajKategorie.this.getFilesDir(), "dane/Kategorie");
        if (!path.exists()) {
            path.mkdir();
        }
        try {
            File file = new File(path, nazwa.getText()+".txt");
            if(file.exists()){
                Toast.makeText(MainDodajKategorie.this, "Już istnieje taka kategoria!", Toast.LENGTH_LONG).show();
                finish();
            }
            FileWriter fileWriter = new FileWriter(file);
            Toast.makeText(MainDodajKategorie.this, "Pomyślnie dodano!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) { }
    }
}
