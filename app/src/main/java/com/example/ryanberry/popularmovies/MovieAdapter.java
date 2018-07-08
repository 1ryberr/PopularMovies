package com.example.ryanberry.popularmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
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
        imageView = new ImageView(mContext);
        if (convertView == null) {
            if ((mContext.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) >=
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {

                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setPadding(0, 0, 0, 0);

                }else{
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(550, 750));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setPadding(0, 0, 0, 0);

                }

            }else{
                imageView.setLayoutParams(new ViewGroup.LayoutParams(700, 850));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(0, 0, 0, 0);
                Log.v(TAG, "Screen os phone size");

            }

        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)

                .load("https://image.tmdb.org/t/p/w185" + movies.get(position).getPosterPath())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(imageView);


        return imageView;
    }

    public void removeItem(int position){
        movies.remove(position);
        notifyDataSetChanged();
    }



}