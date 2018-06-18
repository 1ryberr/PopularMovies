package com.example.ryanberry.popularmovies.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {PopularMovie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase  {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object lock = new Object();
    private static String DATABASE_NAME = "popularmovies";
    private static AppDatabase sInstance;


    public static AppDatabase getInstance(Context context) {
        if(sInstance == null){
            synchronized (lock) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }

        }
        Log.d(LOG_TAG, "Getting the database instance" );
        return sInstance;
    }

    public abstract MovieDOA movieDOA();
}
