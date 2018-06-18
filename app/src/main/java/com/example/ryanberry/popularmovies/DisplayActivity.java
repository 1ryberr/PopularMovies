package com.example.ryanberry.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryanberry.popularmovies.model.AppDatabase;
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
    private ImageButton trailerBtn;
    private ImageButton trailerBtn2;
    private ImageButton addToFavorites;
    private Button reviewsBTN;
    private URL trailerSearchUrl;
    private int id = 0;
    private String videos;
    private List<String> keys;
    private String poster;
    private String title;
    private String releaseDate;
    private String overView;
    private int voteAverage;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mDb = AppDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        poster = intent.getStringExtra("Poster");
        title = intent.getStringExtra("Title");
        releaseDate = intent.getStringExtra("ReleaseDate");
        overView = intent.getStringExtra("OverView");
        voteAverage = intent.getIntExtra("VoteAverage", 0);
        id = intent.getIntExtra("id", 0);

        overViewText = (TextView) findViewById(R.id.overViewTextView);
        titleText = (TextView) findViewById(R.id.titleTextView);
        ratingTextView = (TextView) findViewById(R.id.ratingTextView);
        releaseText = (TextView) findViewById(R.id.releaseDatetextView);

        releaseText.setText(releaseDate);
        overViewText.setText(overView);
        titleText.setText(title);
        ratingTextView.setText(String.valueOf(voteAverage));

        displayImage();
        trailerTask(id);
        buttons();

    }

    private void displayImage() {
        image = (ImageView) findViewById(R.id.imageView);
        Picasso.with(DisplayActivity.this)

                .load("https://image.tmdb.org/t/p/w185" + poster)
                .placeholder(R.mipmap.ic_launcher)
                .into(image);
    }

    private void trailerTask(int id) {

        videos = "/3/movie/" + id + "/videos";
        trailerSearchUrl = NetworkUtils.buildUrl(videos);
        new TheMovieDBTrailerQueryTask().execute(trailerSearchUrl);
    }

    private void buttons() {
        trailerBtn = (ImageButton) findViewById(R.id.trailerBTN);
        trailerBtn2 = (ImageButton) findViewById(R.id.trailerBtn2);
        reviewsBTN = (Button) findViewById(R.id.reviewsBtn);
        addToFavorites = (ImageButton) findViewById(R.id.addToFavorites);


        trailerBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (keys.size() > 1) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + keys.get(1))));
                } else {
                    Toast.makeText(DisplayActivity.this, "There are no more trailers",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (keys.size() > 0) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + keys.get(0))));
                } else {
                    Toast.makeText(DisplayActivity.this, "There are no trailers",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        reviewsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {

                    Intent intent = new Intent(DisplayActivity.this, ReviewsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);

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


        addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        PopularMovie moviePoster = mDb.movieDOA().loadMovieById(id);
                        if (moviePoster == null) {
                            saveData();
                        } else {
                            mDb.movieDOA().deleteTask(moviePoster);
                            Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }
                });

            }
        });
    }

    private void saveData() {

        final PopularMovie popularMovie = new PopularMovie(poster, title, voteAverage, overView, releaseDate, id);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDOA().insertMovie(popularMovie);
            }
        });

        finish();

    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public class TheMovieDBTrailerQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchtrailerUrl = params[0];
            String movieTrailerResults = null;

            try {
                movieTrailerResults = NetworkUtils.getResponseFromHttpUrl(searchtrailerUrl);

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
            return movieTrailerResults;

        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            // mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                keys = JsonUtils.parseMovieTrailerJson(movieSearchResults);

            }


        }

    }


}














