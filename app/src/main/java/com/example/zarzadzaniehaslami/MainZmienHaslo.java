package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainZmienHaslo extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmienhaslo);
    }

    public void zmienHaslo(View view){
        TextView stareHasloTV = findViewById(R.id.EditPass1);
        TextView hasloTV = findViewById(R.id.EditPass2);
        TextView potwierdzHasloTV = findViewById(R.id.EditPass3);
        String stareHaslo = stareHasloTV.getText().toString();
        String haslo = hasloTV.getText().toString();
        String potwierdzHaslo = potwierdzHasloTV.getText().toString();
        if(!haslo.equals(potwierdzHaslo)){
            Toast.makeText(MainZmienHaslo.this, "Hasła się nie zgadzają", Toast.LENGTH_LONG).show();
            return;
        }

        File file = new File(MainZmienHaslo.this.getFilesDir(), "dane/MainHaslo.txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String stareHasloWPliku = bufferedReader.readLine();

            if(!Szyfrowanie.SHA256(stareHaslo).equals(stareHasloWPliku)){
                Toast.makeText(MainZmienHaslo.this, "Złe stare hasło", Toast.LENGTH_LONG).show();
                return;
            }
            FileWriter writer = new FileWriter(file);
            Szyfrowanie.SECRET_KEY = haslo;
            writer.write(Szyfrowanie.SHA256(haslo));
            writer.flush();
            writer.close();
        }catch (Exception e) { }
        finish();
    }
}
