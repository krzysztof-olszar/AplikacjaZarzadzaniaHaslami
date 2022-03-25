package com.example.zarzadzaniehaslami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Zaloguj(View view){
        TextView hasloOkno = (TextView)findViewById(R.id.MainPassword);
        //TextView textview = (TextView) view;//klikniety przycisk
        String haslo=hasloOkno.getText().toString();
        if(haslo.equals("Zosia")){
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }
}