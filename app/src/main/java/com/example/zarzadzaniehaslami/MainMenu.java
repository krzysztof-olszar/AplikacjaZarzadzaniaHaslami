package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenu extends Activity {
    int licznik=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        try {
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DodajActivity(View view){
        Intent intent = new Intent(this, MainDodaj.class);
        startActivity(intent);
        try {
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refresh() throws IOException {
        if(licznik==0){
            ListView listView = findViewById(R.id.GlownaLista);
            List<String> lista = new ArrayList<>();

            File file = new File(MainMenu.this.getFilesDir(), "dane/Baza");
            if (!file.exists()) {
                file.mkdir();
            }
            String[] listaPlikow = file.list();
            Collections.addAll(lista, listaPlikow);


            Adapter adapter = new Adapter(getApplicationContext(), lista);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainMenu.this, MainElement.class);
                    intent.putExtra("nazwa", listaPlikow[i]);
                    startActivity(intent);
                }
            });
        }else if(licznik==1){//ulubione
            ListView listView = findViewById(R.id.GlownaLista);
            List<String> lista = new ArrayList<>();

            File file = new File(MainMenu.this.getFilesDir(), "dane/ulubione.txt");
            if (!file.exists()) {
                file.mkdir();
                licznik = (licznik+1)%2;
                //Log.println(Log.INFO,"x", "why");
                return;
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                lista.add(line);
                line = bufferedReader.readLine();
            }


            Adapter adapter = new Adapter(getApplicationContext(), lista);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainMenu.this, MainElement.class);
                    intent.putExtra("nazwa", lista.get(i));
                    startActivity(intent);
                }
            });
        }
    }

    public void zmienHaslo(View view){
        Intent intent = new Intent(this, MainZmienHaslo.class);
        startActivity(intent);
    }

    public void zmienMenu(View view) throws IOException {
        licznik = (licznik+1)%2;
        //Log.println(Log.INFO,"x", Integer.toString(licznik));
        refresh();
    }

    public void dodajKategorie(View view){
        Intent intent = new Intent(this, MainDodajKategorie.class);
        startActivity(intent);
        try {
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
