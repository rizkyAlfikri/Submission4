package com.dicoding.picodiploma.submission4.db.tvshowdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.submission4.db.Database;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResponse;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.rest.Api;
import com.dicoding.picodiploma.submission4.rest.RetrofitTvShowService;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TvShowRepository {
    private TvShowDao tvShowDao;
    private TvShowWatchDao tvShowWatchDao;
    private LiveData<List<TvShowResults>> mAllTvShow;
    private LiveData<List<TvShowWatchlistModel>> watchAllTv;
    private Api api;

    public TvShowRepository(Application application) {
        Database database = Database.getInstance(application);
        tvShowDao = database.tvShowDao();
        tvShowWatchDao = database.tvShowWatchDao();
        mAllTvShow = tvShowDao.getAllTv();
        watchAllTv = tvShowWatchDao.tGetAllWathlist();
        api = RetrofitTvShowService.createService(Api.class);
    }

    // ===>> Method for favorite tv show <<===

    public LiveData<List<TvShowResults>> getIdTv(int id) {
        return tvShowDao.getTvId(id);
    }

    public LiveData<List<TvShowResults>> getAllTv() {
        return mAllTvShow;
    }

    public void insert(TvShowResults tvShowResults) {
        new insertAsyncTask(tvShowDao).execute(tvShowResults);
    }

    private static class insertAsyncTask extends AsyncTask<TvShowResults, Void, Void> {
        private TvShowDao tvShowDao;

        insertAsyncTask(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(TvShowResults... tvShowResults) {
            tvShowDao.insertTv(tvShowResults[0]);
            return null;
        }
    }

    public void deleteAllTv() {
        new deleteAsyncTask(tvShowDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private TvShowDao tvShowDao;

        deleteAsyncTask(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tvShowDao.deleteAllTv();
            return null;
        }
    }

    public void deleteSelectedTv(TvShowResults tvShowResults) {
        new deletedSelectedTvAsyncTask(tvShowDao).execute(tvShowResults);
    }

    private static class deletedSelectedTvAsyncTask extends AsyncTask<TvShowResults, Void, Void> {
        private TvShowDao tvShowDao;

        deletedSelectedTvAsyncTask(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(TvShowResults... tvShowResults) {
            tvShowDao.deleteSelectedTv(tvShowResults[0]);
            return null;
        }
    }

    // ===>> Method for watchlist tvshow <<===

    public LiveData<List<TvShowWatchlistModel>> getWatchAllTv() {
        return watchAllTv;
    }

    public LiveData<List<TvShowWatchlistModel>> getWatchIdTv(int id) {
        return tvShowWatchDao.tGetIdWatchlist(id);
    }

    public void tWatchInsert(TvShowWatchlistModel showWatchlistModel) {
        new tWatchInsertAsyncTask(tvShowWatchDao).execute(showWatchlistModel);
    }

    private static class tWatchInsertAsyncTask extends AsyncTask<TvShowWatchlistModel, Void, Void> {
        private TvShowWatchDao tvShowWatchDao;

        tWatchInsertAsyncTask(TvShowWatchDao tvShowWatchDao) {
            this.tvShowWatchDao = tvShowWatchDao;
        }

        @Override
        protected Void doInBackground(TvShowWatchlistModel... tvShowWatchlistModels) {
            tvShowWatchDao.tInsertWatchlist(tvShowWatchlistModels[0]);
            return null;
        }
    }

    public void tDeleteSelectedWatch(TvShowWatchlistModel tvShowWatchlistModel) {
        new tDeleteSelectedWatchAsyncTask(tvShowWatchDao).execute(tvShowWatchlistModel);
    }

    private static class tDeleteSelectedWatchAsyncTask extends AsyncTask<TvShowWatchlistModel, Void, Void> {
        private TvShowWatchDao tvShowWatchDao;

        tDeleteSelectedWatchAsyncTask(TvShowWatchDao tvShowWatchDao) {
            this.tvShowWatchDao = tvShowWatchDao;
        }

        @Override
        protected Void doInBackground(TvShowWatchlistModel... tvShowWatchlistModels) {
            tvShowWatchDao.tDeleteSelectedWatchlist(tvShowWatchlistModels[0]);
            return null;
        }
    }

    // ===>> Method for get data tv show from web service <<===
    public MutableLiveData<List<TvShowResults>> getTvData() {
        MutableLiveData<List<TvShowResults>> listTv = new MutableLiveData<>();
        Call<TvShowResponse> call = api.getTvShow(Config.API_KEY);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                listTv.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                listTv.setValue(null);
            }
        });
        return listTv;
    }
}
