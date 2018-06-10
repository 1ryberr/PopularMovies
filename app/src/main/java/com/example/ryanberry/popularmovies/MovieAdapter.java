package com.example.ryanberry.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private static final String TAG = "MovieAdapter";
    private List<PopularMovie> movies;
    private final Context mContext;


    public MovieAdapter(List<PopularMovie> movies, Context mContext) {
        this.movies = movies;
        this.mContext = mContext;
    }

    public int getCount() {
        return movies.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(450, 450));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(2, 2, 2, 2);

        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)

                .load("https://image.tmdb.org/t/p/w185" + movies.get(position).getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);


        return imageView;
    }


}