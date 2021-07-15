package com.example.my_movie_collection.config;

import com.example.my_movie_collection.utils.Utils;

public class AppConfig {

    // == toast offset == //
    public static final int OFFSET = 175;

    // == corner radius == //
    public static final float CORNER_RADIUS = 8f;
    public static final float BLOCK_RADIUS = 7f;

    // == sub image size == //
    private static final int WIDTH_DP = 120;
    private static final int HEIGHT_DP = 120;
    public static final int WIDTH_PX = Utils.dpToPx(WIDTH_DP);
    public static final int HEIGHT_PX = Utils.dpToPx(HEIGHT_DP);

    // == file chooser == //
    public static final int READ_REQUEST_CODE = 42;

    // == intent == //
    public static final String ID = "ID";

    // == strings == //
    public static final String DATE_VIEW = "(click to select date)";
    public static final String MOVIE_ERROR_1 = "Movie name can't be empty!";
    public static final String MOVIE_ERROR_2 = "Movie name is too long! (max 33)";
    public static final String CINEMA_ERROR = "Cinema name is too long! (max 15)";
    public static final String DATE_ERROR = "Date can't be empty!";
    public static final String IMAGE_ERROR = "Image can't be empty!";
}
