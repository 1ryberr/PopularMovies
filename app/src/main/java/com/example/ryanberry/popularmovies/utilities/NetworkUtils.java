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
            "https://api.themoviedb.org/";
    final static String KEY_API_KEY = "api_key";
    final static String API_KEY = "f8bee446e16e33af6dc7bc4f213217f2";
    final static String METHOD_POPULAR = "/3/movie/popular";
    final static String METHOD_TOP_RATED = "/3/movie/top_rated";
    final static String LANGUAGE_KEY = "language";
    final static String LANGUAGE_VALUE = "en-US";
    final static String PAGE_KEY = "page";
    final static String PAGE_VALUE = "1";

    

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
               .path(METHOD_POPULAR)
                .appendQueryParameter(KEY_API_KEY,API_KEY)
                .appendQueryParameter(LANGUAGE_KEY,LANGUAGE_VALUE)
                .appendQueryParameter(PAGE_KEY,PAGE_VALUE)
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
