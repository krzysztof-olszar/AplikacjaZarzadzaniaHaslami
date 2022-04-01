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

public class Adapter extends ArrayAdapter<String> {

    public Adapter(Context context, List<String> list){
        super(context,R.layout.element_list, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.element_list,parent,false);
        }
        //tutaj przypisujemy rzeczy z listy do element_list
        TextView textView = convertView.findViewById(R.id.ServiceName);
        String nazwa = getItem(position);
        textView.setText(nazwa.substring(0,nazwa.length()-4));

        return convertView;
    }
}
