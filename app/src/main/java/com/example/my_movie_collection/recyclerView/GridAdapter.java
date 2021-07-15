package com.example.my_movie_collection.recyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_collection.R;
import com.example.my_movie_collection.config.AppConfig;
import com.example.my_movie_collection.controller.Movie;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private CustomList movies;
    private Context context;

    public GridAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        Bitmap bitmap = movie.getBitmap();

        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        drawable.setCornerRadius(AppConfig.CORNER_RADIUS);

        holder.image.setImageDrawable(drawable);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.toast, (ViewGroup) v.findViewById(R.id.toast_root));

                TextView textView = view.findViewById(R.id.rate);
                textView.setText(String.valueOf(movie.getRating()));

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, AppConfig.OFFSET);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.picture);
        }
    }

    public void setMovies(CustomList movies) {
        this.movies = movies;
    }
}
