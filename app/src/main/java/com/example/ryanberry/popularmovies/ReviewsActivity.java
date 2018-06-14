package com.example.ryanberry.popularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.ryanberry.popularmovies.model.Reviewer;
import com.example.ryanberry.popularmovies.utilities.JsonUtils;
import com.example.ryanberry.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {
    private static final String TAG = "ReviewsActivity";
    private URL reviewSearchUrl;
    private int id = 0;
    private String reviews;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reviews");
        toolbar.setTitle("Reviews");


        listView = (ListView) findViewById(R.id.xmlListView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        reviews = "/3/movie/" + id + "/reviews";
        reviewSearchUrl = NetworkUtils.buildUrl(reviews);
        new TheMovieDBReviewsQueryTask().execute(reviewSearchUrl);


    }

    public class TheMovieDBReviewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchRevewsUrl = params[0];

            String movieReviewResults = null;

            try {

                movieReviewResults = NetworkUtils.getResponseFromHttpUrl(searchRevewsUrl);


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
            return movieReviewResults;

        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            // mLoadingIndicator.setVisibility(View.INVISIBLE);
            List<Reviewer> mReviewer;
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                mReviewer = JsonUtils.parseReviewsJson(movieSearchResults);
                if (mReviewer.size() > 0) {
                    ReviewAdapter movieAdapter = new ReviewAdapter(ReviewsActivity.this, R.layout.list_reviewx, mReviewer);
                    listView.setAdapter(movieAdapter);
                }
            }


        }


    }
}
