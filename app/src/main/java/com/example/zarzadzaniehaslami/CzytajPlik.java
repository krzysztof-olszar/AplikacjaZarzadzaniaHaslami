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
            for(int i=0;temp!=null;i++){
                loginy.add(temp);
                hasla.add(bufferedReader.readLine());
                linki.add(bufferedReader.readLine());
                bufferedReader.readLine();
                temp = bufferedReader.readLine();
                ilosc++;
            }


        }catch (Exception e) {
            Log.println(Log.INFO,"zle", e.toString());
        }
    }

    public void konwertuj(String OldKey) throws IOException {
        fileWriter = new FileWriter(file,false);
        for(int i=0;i<ilosc;i++){
            fileWriter.write(Szyfrowanie.convert(OldKey,loginy.get(i))+"\n");
            fileWriter.write(Szyfrowanie.convert(OldKey,hasla.get(i))+"\n");
            fileWriter.write(Szyfrowanie.convert(OldKey,linki.get(i))+"\n");
            fileWriter.write("----------\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void dodaj(String login, String haslo, String link) throws IOException {
        fileWriter = new FileWriter(file,true);

        fileWriter.write(Szyfrowanie.encrypt(login)+"\n");
        fileWriter.write(Szyfrowanie.encrypt(haslo)+"\n");
        fileWriter.write(Szyfrowanie.encrypt(link)+"\n");
        fileWriter.write("----------\n");

        fileWriter.flush();
        fileWriter.close();
    }

    public void edytuj(String login, String haslo, String link, int licznik) throws IOException {
        try{
            Path path = file.toPath();
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            lines.set(4*licznik, Szyfrowanie.encrypt(login));
            lines.set(4*licznik+1, Szyfrowanie.encrypt(haslo));
            lines.set(4*licznik+2, Szyfrowanie.encrypt(link));
            Files.write(path, lines, StandardCharsets.UTF_8);
        }catch (Exception e) { Log.println(Log.INFO,"c",e.toString());}
    }

}
