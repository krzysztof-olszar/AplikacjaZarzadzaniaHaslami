package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenu extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView listView = findViewById(R.id.GlownaLista);
        List<String> lista = new ArrayList<>();

        File file = new File(MainMenu.this.getFilesDir(), "dane/Baza");
        String[] listaPlikow = file.list();
        Collections.addAll(lista, listaPlikow);


        Adapter adapter = new Adapter(getApplicationContext(), lista);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainMenu.this,MainElement.class);
                intent.putExtra("nazwa", listaPlikow[i]);
                startActivity(intent);
            }
        });
    }
}
