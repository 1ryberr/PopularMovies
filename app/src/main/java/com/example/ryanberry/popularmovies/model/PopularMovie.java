package com.example.ryanberry.popularmovies.model;

public class PopularMovie {
    private String posterPath;
    private String originalTitle;
    private int voteAverage;
    private String overView;
    private String releaseDate;



    public PopularMovie(String posterPath, String originalTitle, int voteAverage, String overView, String releaseDate) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.overView = overView;
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
