package com.example.ryanberry.popularmovies.utilities;

import android.util.Log;

import com.example.ryanberry.popularmovies.model.PopularMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List parseMovieJson(String json) {
        JSONObject movies = null;
        JSONArray results = null;
        String posterPath = null;
        String originalTitle = null;
        int voteAverage = 0;
        String overView = null;
        String releaseDate = null;
        int id = 0;
        List<PopularMovie> myMovies = new ArrayList<PopularMovie>();

        try {

            movies = new JSONObject(json);
            results = movies.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                posterPath = results.getJSONObject(i).getString("poster_path");
                originalTitle = results.getJSONObject(i).getString("original_title");
                voteAverage = results.getJSONObject(i).getInt("vote_average");
                overView = results.getJSONObject(i).getString("overview");
                releaseDate = results.getJSONObject(i).getString("release_date");
                id = results.getJSONObject(i).getInt("id");
                myMovies.add(new PopularMovie(posterPath, originalTitle, voteAverage, overView, releaseDate,id));

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return myMovies;
    }


}
