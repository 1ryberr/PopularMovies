package com.example.ryanberry.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.ryanberry.popularmovies.model.AppDatabase;
import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.example.ryanberry.popularmovies.utilities.JsonUtils;
import com.example.ryanberry.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private GridView gridView;
    private ProgressBar mLoadingIndicator;
    private URL movieSearchUrl;
    private int index = 0;
    private String[] popOrTop = new String[]{"/3/movie/popular", "/3/movie/top_rated"};
    private List<PopularMovie> moviePoster;
    private MovieAdapter movieAdapter = null;
    private AppDatabase mDb;
    private List<PopularMovie> favor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.movie_gridView);
        mDb = AppDatabase.getInstance(getApplicationContext());
        loadOnStartup(savedInstanceState);


    }

    private void loadOnStartup(Bundle savedInstanceState) {
        if (isOnline()) {
            if (savedInstanceState != null) {
                index = savedInstanceState.getInt("movie_options");

            } else {
                index = 0;
            }
            if (index == 2) {
                getSupportActionBar().setTitle("My Favorite Movies");

                loadFavorites();


            } else {
                searchMovies(popOrTop[index], index);
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Network Error");
            builder.setMessage(R.string.error_message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.show();

        }
    }

    private void loadFavorites() {
        getSupportActionBar().setTitle("My Favorite Movies");
        index = 2;
        final LiveData<List<PopularMovie>> popularMovieslist = mDb.movieDOA().loadAllTask();
        popularMovieslist.observe(MainActivity.this, new Observer<List<PopularMovie>>() {
            @Override
            public void onChanged(@Nullable List<PopularMovie> popularMovies) {
                popularMovieslist.removeObserver(this);
                posterClicked(popularMovies);
                favor = popularMovies;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                mLoadingIndicator.setVisibility(View.INVISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

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

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                moviePoster = JsonUtils.parseMovieJson(movieSearchResults);
                posterClicked(moviePoster);

            }

        }
    }

    private void posterClicked(final List<PopularMovie> moviePoster) {
        movieAdapter = new MovieAdapter(moviePoster, MainActivity.this);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (isOnline()) {
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("Poster", moviePoster.get(position).getPosterPath());
                    intent.putExtra("ReleaseDate", moviePoster.get(position).getReleaseDate());
                    intent.putExtra("Title", moviePoster.get(position).getOriginalTitle());
                    intent.putExtra("OverView", moviePoster.get(position).getOverView());
                    intent.putExtra("VoteAverage", moviePoster.get(position).getVoteAverage());
                    intent.putExtra("id", moviePoster.get(position).getId());
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Network Error");
                    builder.setMessage(R.string.error_message);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    builder.show();

                }
            }
        });

    }

    public void searchMovies(String path, int index) {

        String top = "Top Rated Movies";
        String pop = "Most Popular Movies";
        String title = null;

        if (index == 0) {
            title = pop;
        } else if (index == 1) {
            title = top;
        }
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        getSupportActionBar().setTitle(title);
        movieSearchUrl = NetworkUtils.buildUrl(path);
        new TheMovieDBQueryTask().execute(movieSearchUrl);

    }


    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("movie_options", index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.most_popular:

                if (isOnline()) {
                    index = 0;
                    searchMovies(popOrTop[index], index);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Network Error");
                    builder.setMessage(R.string.error_message);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    builder.show();
                }
                return true;

            case R.id.top_rated:

                if (isOnline()) {
                    index = 1;
                    searchMovies(popOrTop[index], index);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Network Error");
                    builder.setMessage(R.string.error_message);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    builder.show();
                }
                return true;

            case R.id.favorite_movies:
                getSupportActionBar().setTitle("My Favorite Movies");
                 loadFavorites();

                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                PopularMovie moviePoster = mDb.movieDOA().loadMovieById(favor.get(position).getId());
                                mDb.movieDOA().deleteTask(moviePoster);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        movieAdapter.removeItem(position);
                                    }
                                });
                            }
                        });


                        return false;
                    }
                });

                index = 2;
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }
}
