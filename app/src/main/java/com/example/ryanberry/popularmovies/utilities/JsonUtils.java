package com.example.ryanberry.popularmovies.utilities;

import android.util.Log;

import com.example.ryanberry.popularmovies.model.PopularMovie;
import com.example.ryanberry.popularmovies.model.Reviewer;

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
                myMovies.add(new PopularMovie(posterPath, originalTitle, voteAverage, overView, releaseDate, id));

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return myMovies;
    }

    public static List parseMovieTrailerJson(String json) {
        JSONObject trailer = null;
        String key = null;
        JSONArray results = null;
        List<String> myTrailers = new ArrayList<String>();

        try {
            trailer = new JSONObject(json);
            results = trailer.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                key = results.getJSONObject(i).getString("key");
                myTrailers.add(key);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return myTrailers;

    }

    public static List<Reviewer> parseReviewsJson(String json) {

        JSONObject reviews = null;
        JSONArray results = null;
        String author = null;
        String content = null;
        String url = null;
        List<Reviewer> myReviews = new ArrayList<Reviewer>();

        try {
            reviews = new JSONObject(json);
            results = reviews.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                author = results.getJSONObject(i).getString("author");
                content = results.getJSONObject(i).getString("content");
                url = results.getJSONObject(i).getString("url");
                myReviews.add(new Reviewer(author, content, url));
           }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return  myReviews ;

    }


}
