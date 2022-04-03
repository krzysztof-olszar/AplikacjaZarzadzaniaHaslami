package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainElement extends Activity {
    int licznik=0;
    CzytajPlik czytajPlik;
    String name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        Intent intent = this.getIntent();
        name = intent.getStringExtra("nazwa");
        String path = "dane/Baza/"+name;
        File file = new File(MainElement.this.getFilesDir(), path);

        czytajPlik = new CzytajPlik(file);
        //Log.println(Log.INFO,"info: ", Integer.toString(czytajPlik.ilosc));
        //Log.println(Log.INFO,"element",file.getPath());


        TextView glownaNazwa = findViewById(R.id.serviceName);
        glownaNazwa.setText(name.substring(0,name.length()-4));
        refresh();
    }

    public void przesunPrawo(View view){
        licznik = (licznik+1) % czytajPlik.ilosc;
        refresh();
    }
    public void przesunLewo(View view){
        licznik--;
        if(licznik<0){
            licznik = czytajPlik.ilosc-1;
        }
        refresh();
    }

    public void refresh(){

        Button buttonLogin = findViewById(R.id.Login);
        Button buttonPassword = findViewById(R.id.Password);
        TextView textViewLinki = findViewById(R.id.Linki);


        buttonLogin.setText(czytajPlik.loginy.get(licznik));
        buttonPassword.setText(czytajPlik.hasla.get(licznik));
        textViewLinki.setText(czytajPlik.linki.get(licznik));
    }

    public void edytuj(View view){
        Intent intent = new Intent(MainElement.this,MainEdytuj.class);
        intent.putExtra("nazwa", name);
        intent.putExtra("login", czytajPlik.loginy.get(licznik));
        intent.putExtra("haslo", czytajPlik.hasla.get(licznik));
        intent.putExtra("linki", czytajPlik.linki.get(licznik));
        intent.putExtra("licznik", Integer.toString(licznik));
        startActivity(intent);
    }
}
