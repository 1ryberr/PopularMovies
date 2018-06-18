package com.example.ryanberry.popularmovies.model;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDOA {

    @Query("SELECT * FROM popularmovie")
    List<PopularMovie> loadAllTask();

    @Insert
    void insertMovie(PopularMovie popularMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void UpdateMovie(PopularMovie popularMovie);

    @Delete
    void deleteTask(PopularMovie popularMovie);

    @Query("SELECT * FROM popularmovie where id =:id")
    PopularMovie loadMovieById(int id);
}
