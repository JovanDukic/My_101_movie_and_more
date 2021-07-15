package com.example.my_movie_collection.utils;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

public class Utils {

    public static int calculateInSampleSize(BitmapFactory.Options options, FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        final int width = options.outWidth;
        final int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;

            while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize = inSampleSize * 2;
            }
        }

        return inSampleSize;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private Utils() {

    }
}
