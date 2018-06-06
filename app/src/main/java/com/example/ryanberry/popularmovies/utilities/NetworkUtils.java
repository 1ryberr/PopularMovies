package com.example.ryanberry.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String GITHUB_BASE_URL =
            "https://api.themoviedb.org/3";

    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    //https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US
    //https://api.themoviedb.org/3/movie/now_playing?api_key=<<api_key>>&language=en-US&page=1
   // https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1




    public static URL buildUrl(String githubSearchQuery) {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
