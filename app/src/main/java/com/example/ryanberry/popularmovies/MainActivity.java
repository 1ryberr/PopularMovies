package com.example.ryanberry.popularmovies;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.example.ryanberry.popularmovies.utilities.JsonUtils;
import com.example.ryanberry.popularmovies.utilities.NetworkUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private GridView gridView;
    private ProgressBar mLoadingIndicator;
    private String METHOD_POPULAR = "/3/movie/popular";
    private String METHOD_TOP_RATED = "/3/movie/top_rated";
    private URL movieSearchUrl;
    private String top = "Top Rated Movies";
    private String pop = "Most Popular Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchMovies(METHOD_POPULAR,pop);
    }


    public class TheMovieDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];


            String movieSearchResults = null;

            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                gridView = (GridView) findViewById(R.id.movie_gridView);
                final List<PopularMovie> array = JsonUtils.parseMovieJson(movieSearchResults);
                MovieAdapter movieAdapter = new MovieAdapter(array, MainActivity.this);
                gridView.setAdapter(movieAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {

                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                        intent.putExtra("Poster", array.get(position).getPosterPath());
                        intent.putExtra("ReleaseDate", array.get(position).getReleaseDate());
                        intent.putExtra("Title", array.get(position).getOriginalTitle());
                        intent.putExtra("OverView", array.get(position).getOverView());
                        intent.putExtra("VoteAverage",array.get(position).getVoteAverage());
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "" + position,
                                Toast.LENGTH_SHORT).show();

                    }
                });


            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });



                AlertDialog dialog = builder.create();
            }
        }



    }

    public void searchMovies(String path, String title){
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        getSupportActionBar().setTitle(title);
        movieSearchUrl = NetworkUtils.buildUrl(path);
        new TheMovieDBQueryTask().execute(movieSearchUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {


      switch (item.getItemId()){

          case R.id.most_popular:
              searchMovies(METHOD_POPULAR,pop);
              return true;

          case R.id.top_rated:
              searchMovies(METHOD_TOP_RATED,top);
              return true;

          default:
              return super.onOptionsItemSelected(item);


      }

    }
}
