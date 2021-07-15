package com.example.my_movie_collection.appWorkers;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import com.example.my_movie_collection.interfaces.ImageGetter;
import com.example.my_movie_collection.utils.Utils;

import java.io.FileDescriptor;
import java.io.IOException;

public class ImageWorker extends AsyncTask<Uri, Void, Bitmap> {

    private ContentResolver contentResolver;
    private ImageGetter imageGetter;

    private int reqWidth;
    private int reqHeight;

    public ImageWorker(ContentResolver contentResolver, int reqWidth, int reqHeight) {
        this.contentResolver = contentResolver;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
        Uri uri = uris[0];
        if (uri != null) {
            try (ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                options.inSampleSize = Utils.calculateInSampleSize(options, fileDescriptor, reqWidth, reqHeight);
                options.inJustDecodeBounds = false;

                return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageGetter.getImage(bitmap);
    }

    public void setImageGetter(ImageGetter imageGetter) {
        this.imageGetter = imageGetter;
    }
}
