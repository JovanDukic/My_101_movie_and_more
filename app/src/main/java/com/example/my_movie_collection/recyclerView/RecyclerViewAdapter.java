package com.example.my_movie_collection.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_collection.R;
import com.example.my_movie_collection.app.ChangeActivity;
import com.example.my_movie_collection.config.AppConfig;
import com.example.my_movie_collection.controller.Movie;
import com.example.my_movie_collection.interfaces.IntentListener;
import com.google.android.gms.ads.InterstitialAd;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private CustomList movies;
    private Context context;

    private IntentListener intentListener;

    private InterstitialAd interstitialAd;

    public RecyclerViewAdapter(Context context, InterstitialAd interstitialAd) {
        this.context = context;
        this.interstitialAd = interstitialAd;
        movies = new CustomList(Movie.class, new CustomCallBack(this));
    }

    private void showInterstitial(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        Bitmap bitmap = movie.getBitmap();

        String rating = "Rating: " + movie.getRating();
        String genre = "Genre: " + movie.getGenre();

        if (movie.getCinema().equals("")) {
            String home = "Home";
            holder.cinema.setText(home);
        } else {
            String cinema = "Cinema: " + movie.getCinema();
            holder.cinema.setText(cinema);
        }

        holder.movieName.setText(movie.getName());
        holder.rating.setText(rating);
        holder.date.setText(movie.getDate());
        holder.genre.setText(genre);

        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        drawable.setCornerRadius(AppConfig.BLOCK_RADIUS);
        holder.image.setImageDrawable(drawable);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
                Intent intent = new Intent(context, ChangeActivity.class);
                intent.putExtra(AppConfig.ID, movie.getID());
                intentListener.startIntent(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, movie.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieName;
        TextView cinema;
        TextView rating;
        TextView date;
        TextView genre;
        ImageView image;

        ImageButton editButton;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            cinema = itemView.findViewById(R.id.cinema);
            rating = itemView.findViewById(R.id.rating);
            date = itemView.findViewById(R.id.date);
            genre = itemView.findViewById(R.id.genre);
            image = itemView.findViewById(R.id.image);

            editButton = itemView.findViewById(R.id.editButton);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public CustomList getMovies() {
        return movies;
    }

    public void setIntentListener(IntentListener intentListener) {
        this.intentListener = intentListener;
    }
}
