package com.example.my_movie_collection.appWorkers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.my_movie_collection.controller.Controller;
import com.example.my_movie_collection.controller.Movie;
import com.example.my_movie_collection.interfaces.Loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class LoadWorker extends AsyncTask<Void, Void, Void> {

    private ArrayList<FileInputStream> inputStreams;

    private Loader loader;

    public LoadWorker(ArrayList<FileInputStream> inputStreams) {
        this.inputStreams = inputStreams;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (FileInputStream inputStream : inputStreams) {
            try (FileChannel channel = inputStream.getChannel()) {
                int channelSize = (int) channel.size();

                ByteBuffer byteBuffer = ByteBuffer.allocate(channelSize);
                channel.read(byteBuffer);
                byteBuffer.flip();

                // == movie params == //
                int ID;
                String name;
                String cinema;
                float rating;
                String date;
                String genre;
                Bitmap bitmap;

                // == ID == //
                ID = byteBuffer.getInt();
                Movie.setNextID(ID + 1);

                // == name == //
                int nameSize = byteBuffer.getInt();
                byte[] nameBytes = new byte[nameSize];
                byteBuffer.get(nameBytes, 0, nameSize);
                name = new String(nameBytes);

                // == cinema == //
                int cinemaSize = byteBuffer.getInt();
                byte[] cinemaBytes = new byte[cinemaSize];
                byteBuffer.get(cinemaBytes, 0, cinemaSize);
                cinema = new String(cinemaBytes);

                // == rating == //
                rating = byteBuffer.getFloat();

                // == date == //
                int dateSize = byteBuffer.getInt();
                byte[] dateBytes = new byte[dateSize];
                byteBuffer.get(dateBytes, 0, dateSize);
                date = new String(dateBytes);

                // == genre == //
                int genreSize = byteBuffer.getInt();
                byte[] genreBytes = new byte[genreSize];
                byteBuffer.get(genreBytes, 0, genreSize);
                genre = new String(genreBytes);

                // == bitmap file == //
                int bitmapFileSize = byteBuffer.getInt();
                byte[] bitmapFileBytes = new byte[bitmapFileSize];
                byteBuffer.get(bitmapFileBytes, 0, bitmapFileSize);
                bitmap = BitmapFactory.decodeByteArray(bitmapFileBytes, 0, bitmapFileSize);

                // == movie == //
                Movie movie = new Movie(ID, name, cinema, rating, date, genre, bitmap);
                Controller.getInstance().addLoadedMovie(movie);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aBoolean) {
        loader.load();
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }
}
