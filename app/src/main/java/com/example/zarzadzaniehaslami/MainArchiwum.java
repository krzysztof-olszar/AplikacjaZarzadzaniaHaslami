package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
            ArchiwumDoWyswietlenia temp = new ArchiwumDoWyswietlenia(czytajPlikArchiwum.nazwa.get(i).substring(0,czytajPlikArchiwum.nazwa.get(i).length()-4),czytajPlikArchiwum.loginy.get(i), czytajPlikArchiwum.hasla.get(i), czytajPlikArchiwum.data.get(i));
            lista.add(temp);
        }


        AdapterArchiwum adapterArchiwum = new AdapterArchiwum(getApplicationContext(), lista);

        listView.setAdapter(adapterArchiwum);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adapterView.getContext(),R.style.popup);
                builder.setTitle("Potwierdzenie");
                builder.setMessage("Przywrócić lub usunąć wpis?");
                builder.setCancelable(false);
                builder.setPositiveButton("Przywróć", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int x) {
                        //nic
                        TextView Nazwa = view.findViewById(R.id.ServiceName);
                        String NazwaS = Nazwa.getText().toString();
                        CzytajPlik czytajPlik = new CzytajPlik(new File(MainArchiwum.this.getFilesDir(),"dane/Baza/"+NazwaS+".txt"));

                        Log.println(Log.INFO,"index",Integer.toString(i));
                        String Login = czytajPlikArchiwum.loginy.get(i);
                        String Haslo = czytajPlikArchiwum.hasla.get(i);
                        String Linki = czytajPlikArchiwum.linki.get(i);
                        try {
                            czytajPlik.dodaj(Login,Haslo,Linki);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        czytajPlikArchiwum.usun(i);
                        dialogInterface.dismiss();
                        try {
                            refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int x) {
                        czytajPlikArchiwum.usun(i);
                        dialogInterface.dismiss();
                        try {
                            refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int x) {
                        //nic
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        /*ImageButton button = (ImageButton)findViewById(R.id.zmienMenu);
        button.setImageResource(R.drawable.commonlist);*/


    }

}
