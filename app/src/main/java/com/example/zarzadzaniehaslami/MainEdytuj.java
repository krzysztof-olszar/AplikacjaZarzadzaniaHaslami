package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainEdytuj extends Activity {
    String name;
    File file;
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
        file = new File(MainEdytuj.this.getFilesDir(), path);

        nazwa = findViewById(R.id.addnazwa);
        login = findViewById(R.id.addlogin);
        haslo = findViewById(R.id.addhaslo);
        linki = findViewById(R.id.addlink);

        nazwa.setText(intent.getStringExtra("nazwa").substring(0,intent.getStringExtra("nazwa").length()-4));
        login.setText(intent.getStringExtra("login"));
        haslo.setText(intent.getStringExtra("haslo"));
        linki.setText(intent.getStringExtra("linki"));
    }

    public void Zapisz(View view){
        try{
            int licznik = Integer.parseInt(this.getIntent().getStringExtra("licznik"));
            Path path = file.toPath();
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            lines.set(4*licznik, Szyfrowanie.encrypt(login.getText().toString()));
            lines.set(4*licznik+1, Szyfrowanie.encrypt(haslo.getText().toString()));
            lines.set(4*licznik+2, Szyfrowanie.encrypt(linki.getText().toString()));
            Files.write(path, lines, StandardCharsets.UTF_8);
            finish();
        }catch (Exception e) { }
    }

}
