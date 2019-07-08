package com.dicoding.picodiploma.submission4.db.moviedb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.submission4.db.Database;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResponse;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.rest.Api;
import com.dicoding.picodiploma.submission4.rest.RetrofitMovieService;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    private MovieDao movieDao;
    private MovieWatchDao movieDaoWatch;
    private LiveData<List<MovieResults>> mAllMovie;
    private LiveData<List<MovieWatchlistModel>> watchAllMovie;
    private Api api;

    public MovieRepository(Application application) {
        Database database = Database.getInstance(application);
        movieDao = database.movieDao();
        movieDaoWatch = database.movieWatchDao();
        mAllMovie = movieDao.getAllMovie();
        watchAllMovie = movieDaoWatch.mGetAllWatchlist();
        api = RetrofitMovieService.createService(Api.class);
    }

    // ===>> Method for favorite movie <<===

    public LiveData<List<MovieResults>> getIdMovie(int id) {
        return movieDao.getMovieId(id);
    }

    // method for calling query
    public LiveData<List<MovieResults>> getAllMovie() {
        return mAllMovie;
    }

    // method for insert data
    public void insert(MovieResults movieResults) {
        new insertAsyncTask(movieDao).execute(movieResults);
    }

    // do insert data in thread worker
    private static class insertAsyncTask extends AsyncTask<MovieResults, Void, Void> {
        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieResults... movieResults) {
            mAsyncTaskDao.insertMovie(movieResults[0]);
            return null;
        }
    }

    // method for delete all movie data
    public void deleteAllMovie() {
        new deleteAllMovieAsyncTask(movieDao).execute();
    }

    // do delete all movie in thread worker
    private static class deleteAllMovieAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao mAsyncTaskDao;

        deleteAllMovieAsyncTask(MovieDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllMovie();
            return null;
        }
    }

    // do delete selected movie in thread worker
    public void deletedSelectedMovie(MovieResults movieResults) {
        new deleteSelectedMovieAsyncTask(movieDao).execute(movieResults);
    }

    private static class deleteSelectedMovieAsyncTask extends AsyncTask<MovieResults, Void, Void> {

        private MovieDao mAsyncTaskDao;

        deleteSelectedMovieAsyncTask(MovieDao dao) {
            this.mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final MovieResults... movieResults) {
            mAsyncTaskDao.deleteSelectedMovie(movieResults[0]);
            return null;
        }
    }

    // ===>> Method for watchlist movie <<===
    public LiveData<List<MovieWatchlistModel>> getWatchIdMovie(int id) {
        return movieDaoWatch.mGetWatchlistId(id);
    }

    public LiveData<List<MovieWatchlistModel>> getWatchAllMovie() {
        return watchAllMovie;
    }

    public void mWachInsert(MovieWatchlistModel movieWatchlistModel) {
        new mWatchInsertAsyncTask(movieDaoWatch).execute(movieWatchlistModel);
    }

    private static class mWatchInsertAsyncTask extends AsyncTask<MovieWatchlistModel, Void, Void> {
        private MovieWatchDao movieWatchDao;

        mWatchInsertAsyncTask(MovieWatchDao movieWatchDao) {
            this.movieWatchDao = movieWatchDao;
        }

        @Override
        protected Void doInBackground(MovieWatchlistModel... movieWatchlistModels) {
            movieWatchDao.mInsertWatchlist(movieWatchlistModels[0]);
            return null;
        }
    }

    public void mDeletedSelectedWatch(MovieWatchlistModel movieWatchlistModel) {
        new mDeletedSelectedWatchAsyncTask(movieDaoWatch).execute(movieWatchlistModel);
    }

    private static class mDeletedSelectedWatchAsyncTask extends AsyncTask<MovieWatchlistModel, Void, Void> {
        private MovieWatchDao movieWatchDao;

        mDeletedSelectedWatchAsyncTask(MovieWatchDao movieWatchDao) {
            this.movieWatchDao = movieWatchDao;
        }

        @Override
        protected Void doInBackground(final MovieWatchlistModel... movieWatchlistModels) {
            movieWatchDao.mDeleteSelectedWatchlist(movieWatchlistModels[0]);
            return null;
        }
    }

    // ===>> Method for get data from web service <<===
    public MutableLiveData<List<MovieResults>> getMovie() {
        MutableLiveData<List<MovieResults>> listMovie = new MutableLiveData<>();
        Call<MovieResponse> call = api. getMovie(Config.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    listMovie.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listMovie.setValue(null);
            }
        });
        return listMovie;
    }
}
