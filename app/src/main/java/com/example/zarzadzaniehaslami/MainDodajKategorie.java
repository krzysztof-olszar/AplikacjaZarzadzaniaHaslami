package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainDodajKategorie extends Activity {
    int wybrany=0;
    String name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_kategorie);

        refreshSpinner();
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
    public void Usun(View view){
        if(wybrany!=0) {
            File doUsuniecia = new File(MainDodajKategorie.this.getFilesDir(), "dane/Kategorie/" + name);
            doUsuniecia.delete();
            wybrany=0;
            refreshSpinner();
        }
    }

    private void refreshSpinner(){
        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerdel);
        ArrayList<String> lista = new ArrayList<String>();

        //dodac rzeczy
        File kategoriePath = new File(MainDodajKategorie.this.getFilesDir(), "dane/Kategorie");
        String[] kategorie = kategoriePath.list();
        lista.add("Brak");
        if(kategorie!=null) {
            for(int i=0;i<kategorie.length;i++){
                kategorie[i] = kategorie[i].substring(0,kategorie[i].length()-4);
            }
            Collections.addAll(lista, Objects.requireNonNull(kategorie));
        }




        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,lista);
        spinner.setAdapter(adp);
        spinner.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                wybrany = position;
                if(position!=0){
                    name = kategorie[position-1]+".txt";
                }
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                //
            }
        });
    }

}
