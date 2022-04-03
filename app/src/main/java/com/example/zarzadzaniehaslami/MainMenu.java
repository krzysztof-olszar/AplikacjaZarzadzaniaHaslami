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
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenu extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        refresh();
    }

    public void DodajActivity(View view){
        Intent intent = new Intent(this, MainDodaj.class);
        startActivity(intent);
        refresh();
    }

    private void refresh(){
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
                Intent intent = new Intent(MainMenu.this,MainElement.class);
                intent.putExtra("nazwa", listaPlikow[i]);
                startActivity(intent);
            }
        });
    }


}
