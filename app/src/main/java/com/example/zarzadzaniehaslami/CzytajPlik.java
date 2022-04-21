package com.example.zarzadzaniehaslami;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CzytajPlik {
    List<String> loginy = new ArrayList<>();
    List<String> hasla = new ArrayList<>();
    List<String> linki = new ArrayList<>();
    FileReader fileReader;
    BufferedReader bufferedReader;
    int ilosc=0;
    File file;
    FileWriter fileWriter;
    CzytajPlik(File plik){
        try{
            this.file = plik;
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String temp = bufferedReader.readLine();
            String GIGAString = "";
            while(temp!=null){
                GIGAString += temp;
                temp = bufferedReader.readLine();
            }
            GIGAString = Szyfrowanie.decrypt(GIGAString);
            Log.println(Log.INFO,"GIGASTRINg",GIGAString);
            String[] linijki = GIGAString.split("\n");
            Log.println(Log.INFO,"loginy[o]",linijki[0]);
            for(int i = 0;i<linijki.length/4;i++){
                loginy.add(linijki[4*i]);
                hasla.add(linijki[4*i+1]);
                linki.add(linijki[4*i+2]);
                ilosc++;
            }


        }catch (Exception e) {
            Log.println(Log.INFO,"zle", e.toString());
        }
    }

    public void konwertuj(String oldKey) throws IOException {
        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        String temp = bufferedReader.readLine();
        String GIGAString = "";
        while (temp != null) {
            GIGAString += temp;
            temp = bufferedReader.readLine();
        }


        fileWriter = new FileWriter(file,false);
        fileWriter.write(Szyfrowanie.convert(oldKey,GIGAString));
        fileWriter.flush();
        fileWriter.close();
    }

    public void dodaj(String login, String haslo, String link) throws IOException {
        String GIGAString = "";
        if(file.exists()) {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String temp = bufferedReader.readLine();
            while (temp != null) {
                GIGAString += temp;
                temp = bufferedReader.readLine();
            }
            GIGAString = Szyfrowanie.decrypt(GIGAString);
        }
        GIGAString += login+"\n";
        GIGAString += haslo+"\n";
        GIGAString += link+"\n";
        GIGAString += "----------\n";

        fileWriter = new FileWriter(file,false);

        fileWriter.write(Szyfrowanie.encrypt(GIGAString));

        fileWriter.flush();
        fileWriter.close();
    }

    public void edytuj(String login, String haslo, String link, int licznik) throws IOException {
        try{
            String GIGAString = "";
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String temp = bufferedReader.readLine();
            while (temp != null) {
                GIGAString += temp;
                temp = bufferedReader.readLine();
            }
            GIGAString = Szyfrowanie.decrypt(GIGAString);

            String[] linijki = GIGAString.split("\n");

            linijki[4*licznik] = login;
            linijki[4*licznik+1] = haslo;
            linijki[4*licznik+2] = link;
            GIGAString = "";
            for(int i = 0;i<linijki.length;i++){
                GIGAString += linijki[i]+"\n";
                Log.println(Log.INFO,"info?", linijki[i]);
            }
            Log.println(Log.INFO,"GIGASTRINg prev",GIGAString);
            fileWriter = new FileWriter(file,false);

            fileWriter.write(Szyfrowanie.encrypt(GIGAString));

            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e) { Log.println(Log.INFO,"c",e.toString());}
    }

}
