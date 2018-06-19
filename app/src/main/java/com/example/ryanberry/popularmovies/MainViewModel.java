package com.example.ryanberry.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ryanberry.popularmovies.model.AppDatabase;
import com.example.ryanberry.popularmovies.model.PopularMovie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<PopularMovie>> popularMovie;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        popularMovie = appDatabase.movieDOA().loadAllTask();
    }

    public LiveData<List<PopularMovie>> getPopularMovie() {
        return popularMovie;
    }

}
