package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    }

    public void Zapisz(View view) throws IOException {
        int licznik = Integer.parseInt(this.getIntent().getStringExtra("licznik"));
        czytajPlik.edytuj(login.getText().toString(),haslo.getText().toString(),linki.getText().toString(),licznik);
        finish();
    }

}
