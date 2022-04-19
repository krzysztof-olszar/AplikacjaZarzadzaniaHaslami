package com.example.zarzadzaniehaslami;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenuKategorie extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_kategorie);

        Intent intent = this.getIntent();
        String name = intent.getStringExtra("nazwa");

        ListView listView = findViewById(R.id.GlownaLista);
        List<String> lista = new ArrayList<>();

        File file = new File(MainMenuKategorie.this.getFilesDir(), "dane/Kategorie/"+name);
        Path path = file.toPath();

        try {
            lista = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Adapter adapter = new Adapter(getApplicationContext(), lista);

        listView.setAdapter(adapter);
        List<String> finalLista = lista;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainMenuKategorie.this, MainElement.class);
                intent.putExtra("nazwa", finalLista.get(i));
                startActivity(intent);
                finish();
            }
        });
    }
}
