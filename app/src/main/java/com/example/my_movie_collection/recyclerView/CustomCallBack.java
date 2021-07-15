package com.example.my_movie_collection.recyclerView;

import androidx.recyclerview.widget.SortedList;

import com.example.my_movie_collection.controller.Movie;
import com.example.my_movie_collection.utils.CustomComparator;

public class CustomCallBack extends SortedList.Callback<Movie> {

    private RecyclerViewAdapter adapter;
    private CustomComparator comparator;

    public CustomCallBack(RecyclerViewAdapter adapter) {
        this.adapter = adapter;
        comparator = new CustomComparator();
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        return comparator.compare(o1, o2);
    }

    @Override
    public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areItemsTheSame(Movie item1, Movie item2) {
        return item1.getID() == item2.getID();
    }

    @Override
    public void onChanged(int position, int count) {
        adapter.notifyItemRangeChanged(position, count);
    }

    @Override
    public void onInserted(int position, int count) {
        adapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        adapter.notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        adapter.notifyItemMoved(fromPosition, toPosition);
    }
}
