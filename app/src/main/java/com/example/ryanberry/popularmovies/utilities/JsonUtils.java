package com.example.ryanberry.popularmovies.utilities;

import android.util.Log;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static PopularMovie[] parseMovieJson(String json) {

        JSONObject movies = null;
        JSONArray results = null;
        String posterPath = null;
        String originalTitle = null;
        int voteAverage = 0;
        String overView = null;
        String releaseDate = null;
        PopularMovie[] myMovies = new PopularMovie[0];

        try {

            movies = new JSONObject(json);
            results = movies.getJSONArray("results");
            myMovies = new PopularMovie[results.length()];

            for (int i = 0; i < results.length(); i++) {

                posterPath = results.getJSONObject(i).getString("poster_path");
                originalTitle = results.getJSONObject(i).getString("original_title");
                voteAverage = results.getJSONObject(i).getInt("vote_average");
                overView = results.getJSONObject(i).getString("overview");
                releaseDate = results.getJSONObject(i).getString("release_date");
                myMovies[i] = new PopularMovie(posterPath, originalTitle, voteAverage, overView, releaseDate);

            }

        } catch (JSONException e) {
                 e.printStackTrace();

        }


        return myMovies;
    }


}
