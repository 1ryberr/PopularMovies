package com.example.ryanberry.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.example.ryanberry.popularmovies.utilities.JsonUtils;
import com.example.ryanberry.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.v(" My stuff", NetworkUtils.buildUrl().toString());


         URL movieSearchUrl = NetworkUtils.buildUrl();
         new TheMovieDBQueryTask().execute(movieSearchUrl);
    }
//    private void makeGithubSearchQuery() {


      //  URL githubSearchUrl = NetworkUtils.buildUrl();

//    }


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
                Log.v(" My stuff",movieSearchResults);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
           // mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
          PopularMovie[] array =  JsonUtils.parseMovieJson(movieSearchResults);


                   Log.v("OnPost", String.valueOf(array[4].getOriginalTitle()));







            } else {

            //    showErrorMessage();
            }
        }
    }









}
