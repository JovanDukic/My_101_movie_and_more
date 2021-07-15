package com.example.my_movie_collection.recyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.SortedList;

import com.example.my_movie_collection.controller.Movie;

import java.util.ArrayList;

public class CustomList extends SortedList<Movie> {

    public CustomList(@NonNull Class<Movie> klass, @NonNull Callback<Movie> callback) {
        super(klass, callback);
    }

    public void removeAll() {
        beginBatchedUpdates();
        for (int i = size() - 1; i >= 0; i--) {
            remove(get(i));
        }
        endBatchedUpdates();
    }

    public void replaceAll(ArrayList<Movie> movies) {
        beginBatchedUpdates();
        for (int i = size() - 1; i >= 0; i--) {
            Movie movie = get(i);
            if (!movies.contains(movie)) {
                remove(movie);
            }
        }
        addAll(movies);
        endBatchedUpdates();
    }

}
