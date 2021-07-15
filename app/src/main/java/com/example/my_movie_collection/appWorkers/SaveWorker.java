package com.example.my_movie_collection.appWorkers;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.my_movie_collection.controller.Movie;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SaveWorker extends AsyncTask<Movie, Void, Void> {

    private FileOutputStream inputStream;

    public SaveWorker(FileOutputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        Movie movie = movies[0];

        try (FileChannel channel = inputStream.getChannel(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            movie.getBitmap().compress(Bitmap.CompressFormat.PNG, 0, outputStream);

            byte[] nameBytes = movie.getName().getBytes();
            byte[] cinemaBytes = movie.getCinema().getBytes();
            byte[] dateBytes = movie.getDate().getBytes();
            byte[] genreBytes = movie.getGenre().getBytes();
            byte[] bitmapFileBytes = outputStream.toByteArray();

            int nameSize = nameBytes.length;
            int cinemaSize = cinemaBytes.length;
            int dateSize = dateBytes.length;
            int genreSize = genreBytes.length;
            int bitmapFileSize = bitmapFileBytes.length;

            ByteBuffer byteBuffer = ByteBuffer.allocate(nameSize + cinemaSize + dateSize + genreSize + bitmapFileSize);

            // == ID == //
            byteBuffer.putInt(movie.getID());
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();

            // == name == //
            byteBuffer.putInt(nameSize);
            byteBuffer.put(nameBytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();

            // == cinema == //
            byteBuffer.putInt(cinemaSize);
            byteBuffer.put(cinemaBytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();

            // == rating == //
            byteBuffer.putFloat(movie.getRating());
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();

            // == date == //
            byteBuffer.putInt(dateSize);
            byteBuffer.put(dateBytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();

            // == genre == //
            byteBuffer.putInt(genreSize);
            byteBuffer.put(genreBytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();

            // == bitmap image == //
            byteBuffer.putInt(bitmapFileSize);
            byteBuffer.put(bitmapFileBytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
