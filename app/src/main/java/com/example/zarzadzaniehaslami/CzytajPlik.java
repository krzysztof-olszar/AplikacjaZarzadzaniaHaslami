package com.example.zarzadzaniehaslami;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CzytajPlik {
    List<String> loginy = new ArrayList<>();
    List<String> hasla = new ArrayList<>();
    List<String> linki = new ArrayList<>();
    FileReader fileReader;
    BufferedReader bufferedReader;
    int ilosc=0;

    CzytajPlik(File file){
        try{
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


}
