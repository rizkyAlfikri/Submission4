package com.dicoding.picodiploma.submission4.db.tvshowdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;

import java.util.List;


@Dao
public interface TvShowWatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long tInsertWatchlist(TvShowWatchlistModel showWatchlistModel);

    @Delete
    void tDeleteSelectedWatchlist(TvShowWatchlistModel showWatchlistModel);

    @Query("DELETE FROM ttshowWatchlist")
    void tDeleteAllWatchlist();

    @Query("SELECT * FROM ttshowWatchlist")
    LiveData<List<TvShowWatchlistModel>> tGetAllWathlist();

    @Query("SELECT * FROM ttshowWatchlist WHERE id = :id")
    LiveData<List<TvShowWatchlistModel>> tGetIdWatchlist(int id);
}
