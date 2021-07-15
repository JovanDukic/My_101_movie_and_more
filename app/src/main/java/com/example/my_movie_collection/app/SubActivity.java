package com.example.my_movie_collection.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.my_movie_collection.R;
import com.example.my_movie_collection.adapters.Adapter;
import com.example.my_movie_collection.appWorkers.ImageWorker;
import com.example.my_movie_collection.appWorkers.SaveWorker;
import com.example.my_movie_collection.config.AppConfig;
import com.example.my_movie_collection.controller.Controller;
import com.example.my_movie_collection.controller.Movie;
import com.example.my_movie_collection.enums.Error;
import com.example.my_movie_collection.interfaces.DateListener;
import com.example.my_movie_collection.interfaces.ImageGetter;
import com.example.my_movie_collection.interfaces.Spinterface;
import com.example.my_movie_collection.listeners.SpinListener;
import com.example.my_movie_collection.utils.DatePicker;

import java.io.IOException;

public class SubActivity extends AppCompatActivity {

    private EditText movieName;
    private EditText cinemaName;
    private RatingBar ratingBar;
    private TextView dateView;
    private ImageView image;

    private String movieTitle;
    private String cinema;
    private String date;
    private String genre;

    private Bitmap bitmapImage;

    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        // == init == //
        init();
        initActions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                ImageWorker imageWorker = new ImageWorker(getContentResolver(), AppConfig.WIDTH_PX, AppConfig.HEIGHT_PX);
                imageWorker.setImageGetter(new ImageGetter() {
                    @Override
                    public void getImage(Bitmap bitmap) {
                        bitmapImage = null;
                        bitmapImage = bitmap;

                        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
                        layoutParams.width = AppConfig.WIDTH_PX;
                        layoutParams.height = AppConfig.HEIGHT_PX;
                        image.setLayoutParams(layoutParams);

                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                        drawable.setCornerRadius(AppConfig.CORNER_RADIUS);

                        image.setImageDrawable(drawable);
                    }
                });
                imageWorker.execute(uri);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.finishButton) {
            movieTitle = movieName.getText().toString().trim();
            cinema = cinemaName.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (!check()) {
                return false;
            }

            Movie movie = new Movie(movieTitle, cinema, rating, date, genre, bitmapImage);
            Controller.getInstance().addMovie(movie);
            save(movie);

            onBackPressed();
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    private boolean check() {
        switch (checkCorrect()) {
            case MOVIE_1:
                Toast.makeText(getApplicationContext(), AppConfig.MOVIE_ERROR_1, Toast.LENGTH_SHORT).show();
                return false;
            case MOVIE_2:
                Toast.makeText(getApplicationContext(), AppConfig.MOVIE_ERROR_2, Toast.LENGTH_SHORT).show();
                return false;
            case CINEMA:
                Toast.makeText(getApplicationContext(), AppConfig.CINEMA_ERROR, Toast.LENGTH_SHORT).show();
                return false;
            case DATE:
                Toast.makeText(getApplicationContext(), AppConfig.DATE_ERROR, Toast.LENGTH_SHORT).show();
                return false;
            case IMAGE:
                Toast.makeText(getApplicationContext(), AppConfig.IMAGE_ERROR, Toast.LENGTH_SHORT).show();
                return false;
            case CLEAR:
            default:
                return true;
        }
    }

    private Error checkCorrect() {
        if (movieTitle.length() == 0) {
            return Error.MOVIE_1;
        } else if (movieTitle.length() > 33) {
            return Error.MOVIE_2;
        } else if (cinema.length() > 15) {
            return Error.CINEMA;
        } else if (dateView.getText().equals(AppConfig.DATE_VIEW)) {
            return Error.DATE;
        } else if (bitmapImage == null) {
            return Error.IMAGE;
        } else {
            return Error.CLEAR;
        }
    }

    private void save(Movie movie) {
        try {
            SaveWorker saveWorker = new SaveWorker(openFileOutput(movie.getFileName(), MODE_APPEND));
            saveWorker.execute(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        initToolbar();
        initComponents();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initComponents() {
        datePicker = new DatePicker();
        datePicker.setDateListener(new DateListener() {
            @Override
            public void getDate(String d) {
                date = new String(d.getBytes());
                dateView.setText(date);
            }
        });

        movieName = findViewById(R.id.movieName);
        cinemaName = findViewById(R.id.cinema);
        ratingBar = findViewById(R.id.rating);
        dateView = findViewById(R.id.date);
        image = findViewById(R.id.image);

        Spinner spinner = findViewById(R.id.customSpin);
        String[] genres = getResources().getStringArray(R.array.Genres);

        SpinListener spinListener = new SpinListener();
        spinListener.setSpinterface(new Spinterface() {
            @Override
            public void getGenre(String g) {
                genre = g;
            }
        });

        spinner.setOnItemSelectedListener(spinListener);
        spinner.setAdapter(new Adapter(this, genres));

    }

    private void initActions() {
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, AppConfig.READ_REQUEST_CODE);
    }
}
