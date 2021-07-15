package com.example.my_movie_collection.utils;

import com.example.my_movie_collection.controller.Controller;
import com.example.my_movie_collection.controller.Movie;

import java.util.ArrayList;

public class CustomFilter {

    public static ArrayList<Movie> filter(String text) {
        String lowerText = text.toLowerCase();
        ArrayList<Movie> movies = new ArrayList<>();

        for (Movie movie : Controller.getInstance().getMovies()) {
            String movieName = movie.getName().toLowerCase();
            if (movieName.contains(lowerText)) {
                movies.add(movie);
            }
        }

        return movies;
    }

}
