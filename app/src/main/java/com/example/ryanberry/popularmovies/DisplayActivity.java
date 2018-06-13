package com.example.ryanberry.popularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.example.ryanberry.popularmovies.utilities.JsonUtils;
import com.example.ryanberry.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";
    private ImageView image;
    private TextView titleText;
    private TextView releaseText;
    private TextView overViewText;
    private TextView ratingTextView;
    private URL movieSearchUrl;
    private int id = 0;
    private String videos;
    private String reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        videos = "/3/movie/" + id + "/videos";


        Intent intent = getIntent();
        String poster = intent.getStringExtra("Poster");
        String title = intent.getStringExtra("Title");
        String releaseDate = intent.getStringExtra("ReleaseDate");
        String overView = intent.getStringExtra("OverView");
        int voteAverage = intent.getIntExtra("VoteAverage", 0);
        id = intent.getIntExtra("id", 0);
        Log.v(TAG, String.valueOf(id));

        overViewText = (TextView) findViewById(R.id.overViewTextView);
        overViewText.setText(overView);

        releaseText = (TextView) findViewById(R.id.releaseDatetextView);
        releaseText.setText(releaseDate);

        titleText = (TextView) findViewById(R.id.titleTextView);
        titleText.setText(title);
        ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        ratingTextView.setText(String.valueOf(voteAverage));

        image = (ImageView) findViewById(R.id.imageView);

        Picasso.with(DisplayActivity.this)

                .load("https://image.tmdb.org/t/p/w185" + poster)
                .placeholder(R.mipmap.ic_launcher)
                .into(image);


        reviews = "/3/movie/" + id + "/reviews";
        movieSearchUrl = NetworkUtils.buildUrl(reviews);
        new TheMovieDBQueryTask().execute(movieSearchUrl);
    }

    public class TheMovieDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieSearchResults = null;

            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.v(TAG, movieSearchResults);

            } catch (IOException e) {
                e.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setTitle("Network Error");
                builder.setMessage(R.string.error_message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.show();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            // mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
            }


        }

    }
}

















