package com.example.my_movie_collection.utils;

import com.example.my_movie_collection.controller.Movie;

import java.util.Comparator;

public class CustomComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getRating() > o2.getRating()) {
            return -1;
        } else if (o1.getRating() < o2.getRating()) {
            return 1;
        } else {
            int result = o1.getName().compareTo(o2.getName());
            return Integer.compare(result, 0);
        }
    }
}
