package com.dicoding.picodiploma.submission4.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.submission4.db.tvshowdb.TvShowRepository;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;

import java.util.List;


public class TvShowViewModel extends AndroidViewModel {
    private TvShowRepository tvShowRepository;
    private LiveData<List<TvShowResults>> mAllTvShow;
    private LiveData<List<TvShowWatchlistModel>> watchAllTvShow;
    private MutableLiveData<List<TvShowResults>> listTv;

    public TvShowViewModel(Application application) {
        super(application);
        tvShowRepository = new TvShowRepository(application);
        mAllTvShow = tvShowRepository.getAllTv();
        watchAllTvShow = tvShowRepository.getWatchAllTv();
        listTv = tvShowRepository.getTvData();
    }

    // ===> Method for favorite tv show <<===
    public LiveData<List<TvShowResults>> getIdTv(int id) {
        return tvShowRepository.getIdTv(id);
    }

    public LiveData<List<TvShowResults>> getAllTv() {
        return mAllTvShow;
    }

    public void insertTv(TvShowResults tvShowResults) {
        tvShowRepository.insert(tvShowResults);
    }

    public void deleteAllTv() {
        tvShowRepository.deleteAllTv();
    }

    public void deleteSelectedTv(TvShowResults tvShowResults) {
        tvShowRepository.deleteSelectedTv(tvShowResults);
    }

    // Method for watchlist tv show
    public LiveData<List<TvShowWatchlistModel>> getWatchAllTvShow() {
        return watchAllTvShow;
    }

    public LiveData<List<TvShowWatchlistModel>> getWatchIdTvShow(int id) {
        return tvShowRepository.getWatchIdTv(id);
    }

    public void insertWatchTv(TvShowWatchlistModel showWatchlistModel) {
        tvShowRepository.tWatchInsert(showWatchlistModel);
    }

    public void deleteSelectedWatchTv(TvShowWatchlistModel showWatchlistModel) {
        tvShowRepository.tDeleteSelectedWatch(showWatchlistModel);
    }

    // ===>> Method to get data from WebService <<===
    public LiveData<List<TvShowResults>> getTvData() {
        return listTv;
    }
}
