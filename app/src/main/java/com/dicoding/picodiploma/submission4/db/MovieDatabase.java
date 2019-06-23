package com.dicoding.picodiploma.submission4.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;

@Database(entities = {MovieResults.class}, exportSchema = false, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
