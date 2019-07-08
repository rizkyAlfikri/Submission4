package com.dicoding.picodiploma.submission4.db.moviedb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;

import java.util.List;


@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MovieResults movieResults);

    @Delete
    void deleteSelectedMovie(MovieResults movieResults);

    @Query("DELETE FROM tmovie")
    void deleteAllMovie();

    @Query("SELECT * FROM tmovie")
    LiveData<List<MovieResults>> getAllMovie();

    @Query("SELECT * FROM tmovie WHERE id = :id")
    LiveData<List<MovieResults>> getMovieId(int id);
}
