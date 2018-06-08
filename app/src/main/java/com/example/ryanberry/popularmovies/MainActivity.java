package com.example.ryanberry.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ryanberry.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.v(" My stuff", NetworkUtils.buildUrl().toString());


         URL githubSearchUrl = NetworkUtils.buildUrl();
         new GithubQueryTask().execute(githubSearchUrl);
    }
//    private void makeGithubSearchQuery() {
//      //  String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl();
//      //  mUrlDisplayTextView.setText(githubSearchUrl.toString());
//        new GithubQueryTask().execute(githubSearchUrl);
//    }


    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        //    mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            Log.v(" My stuff",searchUrl.toString());

           String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                Log.v(" My stuff",githubSearchResults);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
           // mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
           //     showJsonDataView();

            } else {

            //    showErrorMessage();
            }
        }
    }









}
