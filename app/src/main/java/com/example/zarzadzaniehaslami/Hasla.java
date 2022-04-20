package com.example.zarzadzaniehaslami;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Locale;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Hasla {
    public static int silaHasla(String haslo){
        int score=0;
        if(haslo.length()<6){
            return 0;
        }
        score += haslo.length();

        char[] chars = haslo.toCharArray();
        int iloscRoznych=haslo.length();
        for(int i=0;i<haslo.length();i++){
            int temp=0;
            for(int j=0;j<i;j++){
                if(chars[i]==chars[j]){
                    iloscRoznych--;
                    break;
                }
            }
        }
        score += iloscRoznych;
        for(int i=0;i<chars.length;i++){
            String proste = "aeyuio";
            String trudne = "qwrtpsdfghjklzxcvbnm";
            if(proste.indexOf(chars[i])>-1){
                score += 1;
            }else if(trudne.indexOf(chars[i])>-1){
                score += 2;
            }else{
                score += 5;
            }
        }

        return score;
    }

    public static String generujHaslo(int dlugosc,boolean duzeZnakiB, boolean cyfry, boolean znakiSpecjalneB){
        char[] haslo = new char[Math.max(6,dlugosc)];
        String znaki = "qwertyuiopasdfghjklzxcvbnm";
        String liczby = "0123456789";
        String duzeZnaki = znaki.toUpperCase();
        String znakiSpecjalne = "~`!@#$%^&*()-_=+[{]}\\|'\";:/?.>,<";
        if(!duzeZnakiB){
            duzeZnaki = "";
        }
        if(!cyfry){
            liczby = "";
        }
        if(!znakiSpecjalneB){
            znakiSpecjalne = "";
        }

        String turboString = znaki+liczby+duzeZnaki+znakiSpecjalne;
        Random rand = new Random();
        for(int i=0; i<haslo.length;i++){
            int randomNum = rand.nextInt(turboString.length() + 1) ;
            haslo[i] = turboString.charAt(randomNum);
        }
        return new String(haslo);
    }
}
