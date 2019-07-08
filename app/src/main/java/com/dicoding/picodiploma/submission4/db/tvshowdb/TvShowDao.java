package com.dicoding.picodiploma.submission4.db.tvshowdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;

import java.util.List;


@Dao
public interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTv(TvShowResults tvShowResults);

    @Delete
    void deleteSelectedTv(TvShowResults tvShowResults);

    @Query("DELETE FROM ttvshow")
    void deleteAllTv();

    @Query("SELECT * FROM ttvShow")
    LiveData<List<TvShowResults>> getAllTv();

    @Query("SELECT * FROM ttvShow WHERE id = :id")
    LiveData<List<TvShowResults>> getTvId(int id);
}
