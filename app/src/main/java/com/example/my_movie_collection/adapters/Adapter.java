package com.example.my_movie_collection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.example.my_movie_collection.R;

public class Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] genres;

    public Adapter(Context context, String[] genres) {
        inflater = LayoutInflater.from(context);
        this.genres = genres;
    }

    @Override
    public int getCount() {
        return genres.length;
    }

    @Override
    public Object getItem(int position) {
        return genres[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_spinner_dropdown, null);
        CheckedTextView textView = convertView.findViewById(R.id.spinnerText);
        textView.setText(genres[position]);
        return convertView;
    }
}
