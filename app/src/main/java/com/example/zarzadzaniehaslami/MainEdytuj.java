package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainEdytuj extends Activity {
    String name;
    CzytajPlik czytajPlik;
    TextView nazwa;
    TextView login;
    TextView haslo;
    TextView linki;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj);

        Intent intent = this.getIntent();
        name = intent.getStringExtra("nazwa");
        String path = "dane/Baza/"+name;
        czytajPlik = new CzytajPlik(new File(MainEdytuj.this.getFilesDir(), path));

        nazwa = findViewById(R.id.addnazwa);
        login = findViewById(R.id.addlogin);
        haslo = findViewById(R.id.addhaslo);
        linki = findViewById(R.id.addlink);

        nazwa.setText(intent.getStringExtra("nazwa").substring(0,intent.getStringExtra("nazwa").length()-4));
        login.setText(intent.getStringExtra("login"));
        haslo.setText(intent.getStringExtra("haslo"));
        linki.setText(intent.getStringExtra("linki"));

        TextView silaHasla = findViewById(R.id.SilaHasla);
        int Sila = Hasla.silaHasla(haslo.getText().toString());
        if(Sila>70){
            silaHasla.setText("Mocne!");
        }else if(Sila>40){
            silaHasla.setText("Srednie!");
        }else{
            silaHasla.setText("Słabe!");
        }
        haslo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int Sila = Hasla.silaHasla(s.toString());
                if(Sila>70){
                    silaHasla.setText("Mocne!");
                }else if(Sila>40){
                    silaHasla.setText("Srednie!");
                }else{
                    silaHasla.setText("Słabe!");
                }
            }
        });
    }

    public void Zapisz(View view) throws IOException {
        int licznik = Integer.parseInt(this.getIntent().getStringExtra("licznik"));
        czytajPlik.edytuj(login.getText().toString(),haslo.getText().toString(),linki.getText().toString(),licznik);
        finish();
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

        }catch (Exception e){
            dlugoscHasla = 6;
        }
        haslo.setText(Hasla.generujHaslo(dlugoscHasla,duzeZnaki.isChecked(),liczby.isChecked(),znakiSpecjalne.isChecked()));
    }

}
