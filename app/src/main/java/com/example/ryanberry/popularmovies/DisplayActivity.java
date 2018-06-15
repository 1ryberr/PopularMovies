package com.example.ryanberry.popularmovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
    private Button reviewsBTN;
    private URL trailerSearchUrl;
    private int id = 0;
    private String videos;
    private List<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
        String poster = intent.getStringExtra("Poster");
        String title = intent.getStringExtra("Title");
        String releaseDate = intent.getStringExtra("ReleaseDate");
        String overView = intent.getStringExtra("OverView");
        int voteAverage = intent.getIntExtra("VoteAverage", 0);
        id = intent.getIntExtra("id", 0);

        trailerBtn = (ImageButton) findViewById(R.id.trailerBTN);
        trailerBtn2 = (ImageButton) findViewById(R.id.trailerBtn2);
        reviewsBTN = (Button) findViewById(R.id.reviewsBtn);

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


        videos = "/3/movie/" + id + "/videos";
        trailerSearchUrl = NetworkUtils.buildUrl(videos);
        new TheMovieDBTrailerQueryTask().execute(trailerSearchUrl);

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
                Intent intent = new Intent(DisplayActivity.this, ReviewsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });


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














