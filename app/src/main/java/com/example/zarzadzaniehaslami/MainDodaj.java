package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.os.Bundle;
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

            FileWriter writer = new FileWriter(file,true);
            writer.write(login.getText()+"\n");
            writer.write(haslo.getText()+"\n");
            writer.write(linki.getText()+"\n");
            writer.write("----------\n");
            writer.flush();
            writer.close();
            Toast.makeText(MainDodaj.this, "Pomyślnie dodano!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) { }
    }
}
