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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainElement extends Activity {
    int licznik=0;
    CzytajPlik czytajPlik;
    String name;
    File file;
    int poprzedniaKategoria=0;
    File kategoriePath;
    String[] kategorie;
    boolean ulubione = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);

        Intent intent = this.getIntent();
        name = intent.getStringExtra("nazwa");
        String path = "dane/Baza/"+name;
        file = new File(MainElement.this.getFilesDir(), path);

        czytajPlik = new CzytajPlik(file);
        Log.println(Log.INFO,"info: ", Integer.toString(czytajPlik.ilosc));
        //Log.println(Log.INFO,"element",file.getPath());

        try {
            File ulubioneFile = new File(MainElement.this.getFilesDir(), "dane/ulubione.txt");
            FileReader fileReader = null;
            fileReader = new FileReader(ulubioneFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temp = bufferedReader.readLine();
            while (temp != null) {
                if (temp.equals(name)) {
                    ulubione = true;
                    ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
                    button.setImageResource(R.drawable.star);
                    break;
                }
                temp = bufferedReader.readLine();
            }
        }catch (Exception e) { Log.println(Log.INFO,"Exception: ",e.toString());}

        TextView glownaNazwa = findViewById(R.id.serviceName);
        glownaNazwa.setText(name.substring(0,name.length()-4));

        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerdel);
        ArrayList<String> lista = new ArrayList<String>();

        //dodac rzeczy
        kategoriePath = new File(MainElement.this.getFilesDir(), "dane/Kategorie");
        kategorie = kategoriePath.list();
        lista.add("Brak kategorii");
        if(kategorie!=null) {
            for(int i=0;i<kategorie.length;i++){
                kategorie[i] = kategorie[i].substring(0,kategorie[i].length()-4);
            }
            Collections.addAll(lista, Objects.requireNonNull(kategorie));

            //znajdz w ktorej kategorii jest obecnie
            kategorie = kategoriePath.list();
            outerloop:
            for(int i=0;i<kategorie.length;i++){
                File kategoria = new File(kategoriePath, kategorie[i]);
                try {
                    FileReader fileReader = new FileReader(kategoria);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String temp = bufferedReader.readLine();
                    while(temp!=null){
                        if(temp.equals(name)){
                            poprzedniaKategoria = i+1;
                            break outerloop;
                        }
                        temp = bufferedReader.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,lista);
        spinner.setAdapter(adp);
        spinner.setSelection(poprzedniaKategoria);
        spinner.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                if(poprzedniaKategoria==position){
                    return;
                }
                if(poprzedniaKategoria!=0){
                    //usun
                    File file = new File(kategoriePath, kategorie[poprzedniaKategoria-1]);

                    Path path = file.toPath();
                    List<String> lines = null;
                    try {
                        lines = Files.readAllLines(path, StandardCharsets.UTF_8);

                        for(int l=0;l<lines.size();l++) {
                                if(lines.get(l).equals(name)){
                                    lines.remove(l);
                                    break;
                                }
                        }
                        Files.write(path, lines, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(position!=0){
                    //dodaj
                    File file = new File(kategoriePath, kategorie[position-1]);
                    try {
                        FileWriter fileWriter = new FileWriter(file,true);
                        fileWriter.write(name+"\n");
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                poprzedniaKategoria = position;
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                //
            }
        });



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
        builder.setMessage("Czy na pewno chcesz usun???? konto?");
        builder.setCancelable(false);
        builder.setPositiveButton("Usu??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //usuwa
                //najpierw dodaj do archiwum
                CzytajPlikArchiwum czytajPlikArchiwum = new CzytajPlikArchiwum(new File(MainElement.this.getFilesDir(), "dane/Archiwum.txt"));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    czytajPlikArchiwum.dodaj(name,czytajPlik.loginy.get(licznik),czytajPlik.hasla.get(licznik),czytajPlik.linki.get(licznik),sdf.format(new Date()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //nastepnie usun
                if(czytajPlik.ilosc==1){
                    //usuwa z kategorii
                    if(poprzedniaKategoria!=0){
                        //usun
                        File file = new File(kategoriePath, kategorie[poprzedniaKategoria-1]);

                        Path path = file.toPath();
                        List<String> lines = null;
                        try {
                            lines = Files.readAllLines(path, StandardCharsets.UTF_8);

                            for(int l=0;l<lines.size();l++) {
                                if(lines.get(l).equals(name)){
                                    lines.remove(l);
                                    break;
                                }
                            }
                            Files.write(path, lines, StandardCharsets.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(ulubione){
                        try {
                            File ulubioneFile = new File(MainElement.this.getFilesDir(), "dane/ulubione.txt");
                            FileReader fileReader = new FileReader(ulubioneFile);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            List<String> list = new ArrayList<>();
                            String temp = bufferedReader.readLine();
                            while (temp != null) {
                                if (!temp.equals(name)) {
                                    list.add(temp);
                                }
                                temp = bufferedReader.readLine();
                            }
                            FileWriter fileWriter = new FileWriter(ulubioneFile, false);
                            for (int l = 0; l < list.size(); l++) {
                                fileWriter.write(list.get(l));
                            }
                            fileWriter.flush();
                            fileWriter.close();
                            ulubione = false;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }



                    file.delete();
                    Toast.makeText(MainElement.this, "Pomy??lnie usuni??to", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    try{
                        String GIGAString = "";
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        String temp = bufferedReader.readLine();
                        while (temp != null) {
                            GIGAString += temp;
                            temp = bufferedReader.readLine();
                        }
                        GIGAString = Szyfrowanie.decrypt(GIGAString);

                        String[] linijki = GIGAString.split("\n");

                        GIGAString = "";
                        for(int j = 0;j<linijki.length;j++){
                            if((j-j%4)/4==licznik){
                                continue;
                            }
                            GIGAString += linijki[j]+"\n";
                            //Log.println(Log.INFO,"info?", linijki[i]);
                        }
                        //Log.println(Log.INFO,"GIGASTRINg prev",GIGAString);
                        FileWriter fileWriter = new FileWriter(file, false);

                        fileWriter.write(Szyfrowanie.encrypt(GIGAString));

                        fileWriter.flush();
                        fileWriter.close();

                        czytajPlik.loginy.remove(licznik);
                        czytajPlik.hasla.remove(licznik);
                        czytajPlik.linki.remove(licznik);
                        czytajPlik.ilosc--;
                        licznik = (licznik+1) % czytajPlik.ilosc;
                        Toast.makeText(MainElement.this, "Pomy??lnie usuni??to", Toast.LENGTH_SHORT).show();
                        refresh();
                    }catch (Exception e) {Log.println(Log.INFO,"c","Tutaj blad:"+e.toString()); }
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

    public void skopiuj(View view){//schowek
        TextView textview = (TextView) view;

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("ZH",textview.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

    public void zmienUlubione(View view) throws IOException {
        File ulubioneFile = new File(MainElement.this.getFilesDir(), "dane/ulubione.txt");
        if(!ulubione){
            FileWriter fileWriter = new FileWriter(ulubioneFile, true);
            fileWriter.write(name+"\n");
            fileWriter.flush();
            fileWriter.close();
            ulubione = true;


            ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
            button.setImageResource(R.drawable.star);
        }else{
            FileReader fileReader = new FileReader(ulubioneFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> list = new ArrayList<>();
            String temp = bufferedReader.readLine();
            while(temp!=null){
                if(!temp.equals(name)){
                    list.add(temp);
                }
                temp = bufferedReader.readLine();
            }
            FileWriter fileWriter = new FileWriter(ulubioneFile, false);
            for(int i=0;i<list.size();i++){
                fileWriter.write(list.get(i));
            }
            fileWriter.flush();
            fileWriter.close();
            ulubione = false;

            ImageButton button = (ImageButton)findViewById(R.id.imageButton4);
            button.setImageResource(R.drawable.falsestar);
        }
    }

    @Override
    public void onResume () {
        super.onResume();
        reload();
        refresh();
    }

    private void reload(){
        czytajPlik = new CzytajPlik(file);
    }

}
