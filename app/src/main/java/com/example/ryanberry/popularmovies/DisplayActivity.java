package com.example.ryanberry.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";
    private ImageView image;
    private TextView titleText;
    private TextView releaseText;
    private TextView overViewText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
        String poster = intent.getStringExtra("Poster");
        String title = intent.getStringExtra("Title");
        String releaseDate = intent.getStringExtra("ReleaseDate");
        String overView = intent.getStringExtra("OverView");


        overViewText = (TextView) findViewById(R.id.overViewTextView);
        overViewText.setText(overView);

        releaseText = (TextView) findViewById(R.id.releaseTextView);
        releaseText.setText(releaseDate);

        titleText = (TextView) findViewById(R.id.titleTextView);
        titleText.setText(title);


        image = (ImageView) findViewById(R.id.imageView);

        Picasso.with(DisplayActivity.this)

                .load("https://image.tmdb.org/t/p/w185" + poster)
                .placeholder(R.mipmap.ic_launcher)
                .into(image);


        Log.v(TAG, poster);
    }

}
