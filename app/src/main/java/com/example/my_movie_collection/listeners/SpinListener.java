package com.example.my_movie_collection.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.my_movie_collection.interfaces.Spinterface;

public class SpinListener implements AdapterView.OnItemSelectedListener {

    private Spinterface spinterface;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinterface.getGenre((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setSpinterface(Spinterface spinterface) {
        this.spinterface = spinterface;
    }
}
