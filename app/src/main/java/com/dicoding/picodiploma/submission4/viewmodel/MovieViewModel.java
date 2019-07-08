package com.dicoding.picodiploma.submission4.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.submission4.db.moviedb.MovieRepository;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<MovieResults>> mAllMovie;
    private LiveData<List<MovieWatchlistModel>> mAllWatchMovie;
    private MutableLiveData<List<MovieResults>> listMovie;

    public MovieViewModel(Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        mAllMovie = movieRepository.getAllMovie();
        mAllWatchMovie = movieRepository.getWatchAllMovie();
        listMovie = movieRepository.getMovie();
    }

    // ===>> Method for favorite movie <<===

    public LiveData<List<MovieResults>> getIdMovie(int id) {
        return movieRepository.getIdMovie(id);
    }

    // Method for query all movie data from Room SQLite
    public LiveData<List<MovieResults>> getAllMovie() {
        return mAllMovie;
    }

    // Method for insert movie data
    public void insert(MovieResults movieResults) {
        movieRepository.insert(movieResults);
    }

    // Method for Delete all movie data
    public void deleteAllMovie() {
        movieRepository.deleteAllMovie();
    }

    // Method for Delete selected movie data
    public void deleteSelectedMovie(MovieResults movieResults) {
        movieRepository.deletedSelectedMovie(movieResults);
    }


    // ===>> Method for watchlist movie <<===

    public LiveData<List<MovieWatchlistModel>> getIdWatchMovie(int id) {
        return movieRepository.getWatchIdMovie(id);
    }

    public LiveData<List<MovieWatchlistModel>> getAllWatchMovie() {
        return mAllWatchMovie;
    }

    public void insertWatchMovie(MovieWatchlistModel movieWatchlistModel) {
        movieRepository.mWachInsert(movieWatchlistModel);
    }

    public void deleteSelectedWatchMovie(MovieWatchlistModel movieWatchlistModel) {
        movieRepository.mDeletedSelectedWatch(movieWatchlistModel);
    }

    // ===>> Method for get movie data from WebService <<===
    public LiveData<List<MovieResults>> getMovieData() {
        return listMovie;
    }
}
