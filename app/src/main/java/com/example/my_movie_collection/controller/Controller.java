package com.example.my_movie_collection.controller;

import com.example.my_movie_collection.interfaces.UpdateList;

import java.io.File;
import java.util.ArrayList;

public class Controller {
    private static final Controller ourInstance = new Controller();

    private final ArrayList<Movie> movies = new ArrayList<>();

    private File root;

    private UpdateList updateList;

    private boolean isFistLaunch = true;

    public void addMovie(Movie movie) {
        movies.add(movie);
        updateList.update(movie);
    }

    public void addLoadedMovie(Movie movie) {
        movies.add(movie);
    }

    public boolean isFistLaunch() {
        return isFistLaunch;
    }

    public void setFistLaunch(boolean fistLaunch) {
        isFistLaunch = fistLaunch;
    }

    public static Controller getInstance() {
        return ourInstance;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void update(){
        updateList.change();
    }

    public void setRoot(File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    private Controller() {

    }

    public void setUpdateList(UpdateList updateList) {
        this.updateList = updateList;
    }
}
