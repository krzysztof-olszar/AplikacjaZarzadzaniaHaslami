package com.example.zarzadzaniehaslami;

import android.text.Editable;
import android.text.TextWatcher;

public class HasloObserver implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Hasla.silaHasla(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
