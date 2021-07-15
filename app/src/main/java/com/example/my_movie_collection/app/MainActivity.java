package com.example.my_movie_collection.app;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_collection.R;
import com.example.my_movie_collection.ads.AdUnitIds;
import com.example.my_movie_collection.ads.AdUtils;
import com.example.my_movie_collection.appWorkers.LoadWorker;
import com.example.my_movie_collection.controller.Controller;
import com.example.my_movie_collection.controller.Movie;
import com.example.my_movie_collection.interfaces.IntentListener;
import com.example.my_movie_collection.interfaces.Loader;
import com.example.my_movie_collection.interfaces.UpdateList;
import com.example.my_movie_collection.recyclerView.GridAdapter;
import com.example.my_movie_collection.recyclerView.RecyclerViewAdapter;
import com.example.my_movie_collection.utils.CustomFilter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton actionButton;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private GridAdapter gridAdapter;

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller.getInstance().setRoot(getFilesDir());

        Controller.getInstance().setUpdateList(new UpdateList() {
            @Override
            public void update(Movie movie) {
                adapter.getMovies().add(movie);
            }

            @Override
            public void change() {
                adapter.getMovies().removeAll();
                adapter.getMovies().addAll(Controller.getInstance().getMovies());
            }
        });

        init();
        loadAds();

        if (Controller.getInstance().isFistLaunch()) {
            load();
        } else {
            initRecyclerView();
        }

    }

    private void load() {
        File[] files = getFilesDir().listFiles();
        ArrayList<FileInputStream> inputStreams = new ArrayList<>();
        for (File file : files) {
            try {
                inputStreams.add(openFileInput(file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LoadWorker loadWorker = new LoadWorker(inputStreams);
        loadWorker.setLoader(new Loader() {
            @Override
            public void load() {
                initRecyclerView();
            }

            @Override
            public void startIntent(Intent intent) {
                startActivity(intent);
            }
        });
        loadWorker.execute();

        Controller.getInstance().setFistLaunch(false);
    }

    private void init() {
        initToolbar();
        initButtons();
        initActions();
        initAds();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new RecyclerViewAdapter(getApplicationContext(), interstitialAd);
        adapter.setIntentListener(new IntentListener() {
            @Override
            public void startIntent(Intent intent) {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        });
        adapter.getMovies().addAll(Controller.getInstance().getMovies());

        gridAdapter = new GridAdapter(getApplicationContext());
        gridAdapter.setMovies(adapter.getMovies());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initButtons() {
        actionButton = findViewById(R.id.fab);
    }

    private void initActions() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performIntent();
                showInterstitial();
            }
        });
    }

    private void initAds() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AdUnitIds.INTERSTITIAL_ID);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadAds();
            }
        });
    }

    private void loadAds() {
        if (isNetworkConnected()) {
            interstitialAd.loadAd(AdUtils.buildRequest());
        }
    }

    private void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Movie> movies = CustomFilter.filter(newText);
                adapter.getMovies().replaceAll(movies);
                recyclerView.scrollToPosition(0);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.listButton && !(recyclerView.getAdapter() instanceof RecyclerViewAdapter)) {
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        } else if (id == R.id.gridButton && !(recyclerView.getAdapter() instanceof GridAdapter)) {
            recyclerView.setAdapter(gridAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void performIntent() {
        startActivity(new Intent(getApplicationContext(), SubActivity.class));
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
