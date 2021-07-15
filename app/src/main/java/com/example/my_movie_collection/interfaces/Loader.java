package com.example.my_movie_collection.interfaces;

import android.content.Intent;

import com.example.my_movie_collection.controller.Movie;

public interface Loader {

    void load();

    void startIntent(Intent intent);

}
