package com.example.ryanberry.popularmovies;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.example.ryanberry.popularmovies.utilities.JsonUtils;
import com.example.ryanberry.popularmovies.utilities.NetworkUtils;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL movieSearchUrl = NetworkUtils.buildUrl();
        new TheMovieDBQueryTask().execute(movieSearchUrl);
    }


    public class TheMovieDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];


            String movieSearchResults = null;

            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.v(" My stuff", movieSearchResults);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            // mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                gridView =(GridView) findViewById(R.id.movie_gridView);
                List<PopularMovie> array = JsonUtils.parseMovieJson(movieSearchResults);
                MovieAdapter movieAdapter = new MovieAdapter(array,MainActivity.this);
                gridView.setAdapter(movieAdapter);

                Log.v("OnPost", String.valueOf(array.get(4).getOriginalTitle()));


            } else {

                //    showErrorMessage();
            }
        }
    }


}
