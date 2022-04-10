package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainHaslo extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haslo);
    }



    public void potwierdzHaslo(View view){
        TextView hasloTV = findViewById(R.id.FirstPass);
        TextView potwierdzHasloTV = findViewById(R.id.FirstPowtorz);
        String haslo = hasloTV.getText().toString();
        String potwierdzHaslo = potwierdzHasloTV.getText().toString();
        if(!haslo.equals(potwierdzHaslo)){
            Toast.makeText(MainHaslo.this, "Hasła się nie zgadzają", Toast.LENGTH_LONG).show();
            return;
        }
        File file = new File(MainHaslo.this.getFilesDir(), "dane/MainHaslo.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(Szyfrowanie.SHA256(haslo));
            writer.flush();
            writer.close();
        }catch (Exception e) { }
        finish();
    }
}
