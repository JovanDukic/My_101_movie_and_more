package com.example.my_movie_collection.ads;

import com.google.android.gms.ads.AdRequest;

public class AdUtils {

    public static AdRequest buildRequest() {
        return new AdRequest.Builder()
                .addTestDevice("78F90FCE4CB9659A431FF081A240E056")
                .build();
    }

    private AdUtils() {

    }

}
