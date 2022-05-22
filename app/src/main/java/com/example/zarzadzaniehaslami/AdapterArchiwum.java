package com.example.zarzadzaniehaslami;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterArchiwum extends ArrayAdapter<ArchiwumDoWyswietlenia> {

    public AdapterArchiwum(Context context, List<ArchiwumDoWyswietlenia> list){
        super(context,R.layout.element_archiwum, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.element_archiwum,parent,false);
        }
        //tutaj przypisujemy rzeczy z listy do element_list
        ArchiwumDoWyswietlenia obiekt = getItem(position);

        TextView Nazwa = convertView.findViewById(R.id.ServiceName);
        Nazwa.setText(obiekt.nazwa);

        TextView Login = convertView.findViewById(R.id.Login);
        Login.setText(obiekt.login);

        TextView Haslo = convertView.findViewById(R.id.Haslo);
        Haslo.setText(obiekt.haslo);

        TextView Date = convertView.findViewById(R.id.Date);
        Date.setText(obiekt.data);

        return convertView;
    }
}
