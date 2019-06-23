package com.dicoding.picodiploma.submission4.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MovieResults movieResults);

    @Update
    void updateMovie(MovieResults... movieResults);

    @Delete
    void deleteMovie(MovieResults... movieResults);

    @Query("SELECT * FROM tmovie")
    List<MovieResults> getAllMovie();

    @Query("SELECT * FROM tmovie WHERE isWatchlist = :isWatchlist")
    List<MovieResults> getMovieWatchlist(boolean isWatchlist);

    @Query("SELECT * FROM tmovie WHERE id = :id")
    List<MovieResults> getMovieId(int id);




}
