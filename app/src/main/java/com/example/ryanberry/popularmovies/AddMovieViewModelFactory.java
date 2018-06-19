package com.example.ryanberry.popularmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.ryanberry.popularmovies.model.AppDatabase;

public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

private final AppDatabase mDb;
private final int mMovieId;

    public AddMovieViewModelFactory(AppDatabase mDb, int mMovieId) {
        this.mDb = mDb;
        this.mMovieId = mMovieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddMovieViewModel(mDb,mMovieId);
    }
}
