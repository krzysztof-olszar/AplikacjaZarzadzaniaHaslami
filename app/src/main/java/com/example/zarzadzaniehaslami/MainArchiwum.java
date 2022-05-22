package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainArchiwum extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archiwum);
        try {
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refresh() throws IOException {
        ListView listView = findViewById(R.id.GlownaLista);
        List<ArchiwumDoWyswietlenia> lista = new ArrayList<>();

        File file = new File(MainArchiwum.this.getFilesDir(), "dane/Archiwum.txt");
        if (!file.exists()) {
            return;
        }/////
        CzytajPlikArchiwum czytajPlikArchiwum = new CzytajPlikArchiwum(file);

        for(int i=0;i<czytajPlikArchiwum.ilosc;i++){
            ArchiwumDoWyswietlenia temp = new ArchiwumDoWyswietlenia(czytajPlikArchiwum.nazwa.get(i),czytajPlikArchiwum.loginy.get(i), czytajPlikArchiwum.hasla.get(i), czytajPlikArchiwum.data.get(i));
            lista.add(temp);
        }


        AdapterArchiwum adapterArchiwum = new AdapterArchiwum(getApplicationContext(), lista);

        listView.setAdapter(adapterArchiwum);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Intent intent = new Intent(MainMenu.this, MainElement.class);
                intent.putExtra("nazwa", listaPlikow[i]);
                startActivity(intent);*/
            }
        });
        /*ImageButton button = (ImageButton)findViewById(R.id.zmienMenu);
        button.setImageResource(R.drawable.commonlist);*/


    }
}
