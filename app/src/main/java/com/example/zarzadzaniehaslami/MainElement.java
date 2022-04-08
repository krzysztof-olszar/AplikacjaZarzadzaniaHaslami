package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainElement extends Activity {
    int licznik=0;
    CzytajPlik czytajPlik;
    String name;
    File file;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        Intent intent = this.getIntent();
        name = intent.getStringExtra("nazwa");
        String path = "dane/Baza/"+name;
        file = new File(MainElement.this.getFilesDir(), path);

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

    public void usun(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.popup);
        builder.setTitle("Potwierdzenie");
        builder.setMessage("Czy na pewno chcesz usunąć konto?");
        builder.setCancelable(false);
        builder.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //usuwa
                if(czytajPlik.ilosc==1){
                    file.delete();
                    finish();
                }else{
                    try{
                        Path path = file.toPath();
                        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                        /*for(int l=0;l<lines.size();l++) {
                            Log.println(Log.INFO, "wypisz", lines.get(l));
                        }*/
                        for(int l=0;l<4;l++) {
                            lines.remove(4*licznik);
                        }

                        czytajPlik.loginy.remove(licznik);
                        czytajPlik.hasla.remove(licznik);
                        czytajPlik.linki.remove(licznik);
                        czytajPlik.ilosc--;
                        licznik = (licznik+1) % czytajPlik.ilosc;
                        refresh();

                        Files.write(path, lines, StandardCharsets.UTF_8);
                    }catch (Exception e) { }
                }
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nic
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void skopiuj(View view){
        TextView textview = (TextView) view;

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("ZH",textview.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

}
