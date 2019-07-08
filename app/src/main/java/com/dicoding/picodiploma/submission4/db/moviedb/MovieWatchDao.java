package com.dicoding.picodiploma.submission4.db.moviedb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;

import java.util.List;


@Dao
public interface MovieWatchDao {
    // Query for movie watchlist table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long mInsertWatchlist(MovieWatchlistModel movieWatchlistModel);

    @Delete
    void mDeleteSelectedWatchlist(MovieWatchlistModel movieWatchlistModel);

    @Query("SELECT * FROM tmovieWatchlist")
    LiveData<List<MovieWatchlistModel>> mGetAllWatchlist();

    @Query("SELECT * FROM tmovieWatchlist WHERE id = :id")
    LiveData<List<MovieWatchlistModel>> mGetWatchlistId(int id);

}
