package com.example.my_movie_collection.controller;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class Movie {

    private static int nextID;
    private int ID;

    private String name;
    private String cinema;
    private float rating;
    private String date;
    private String genre;
    private Bitmap bitmap;

    private String fileName;
    private String bitmapFile;

    public Movie(String name, String cinema, float rating, String date, String genre, Bitmap bitmap) {
        this.ID = nextID++;
        this.name = name;
        this.cinema = cinema;
        this.rating = rating;
        this.date = date;
        this.genre = genre;
        this.bitmap = bitmap;
        setFileName();
        setBitmapFile();
    }

    public Movie(int ID, String name, String cinema, float rating, String date, String genre, Bitmap bitmap) {
        this.ID = ID;
        this.name = name;
        this.cinema = cinema;
        this.rating = rating;
        this.date = date;
        this.genre = genre;
        this.bitmap = bitmap;
        setFileName();
        setBitmapFile();
    }

    static {
        nextID = 0;
    }

    public void setFileName() {
        this.fileName = String.valueOf(ID).concat("_").concat(name).concat(".movie");
    }

    private void setBitmapFile() {
        bitmapFile = String.valueOf(ID).concat("_").concat(name).concat(".image");
    }

    public String getFileName() {
        return fileName;
    }

    public static void setNextID(int nextID) {
        Movie.nextID = nextID;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;

        Movie movie = (Movie) obj;

        return movie.ID == this.ID && movie.name.equals(this.name) && movie.cinema.equals(this.cinema) && movie.rating == this.rating && movie.date.equals(this.date);
    }


    // == setters == //

    public void setName(String name) {
        this.name = name;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    // == getters == //
    public int getID() {
        return ID;
    }

    public String getBitmapFile() {
        return bitmapFile;
    }

    public String getName() {
        return name;
    }

    public String getCinema() {
        return cinema;
    }

    public float getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public String getGenre() {
        return genre;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
