package com.dicoding.picodiploma.submission4.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

public class MovieRepository {
    private MovieDatabase database;

    public MovieRepository(Context context) {
        database = Room.databaseBuilder(context, MovieDatabase.class, Config.DB_MOVIE_NAME).build();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertedTask(final MovieResults movieResults) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.movieDao().insertMovie(movieResults);
                return null;
            }
        }.execute();
    }

    // hapus movie berdasarkan id
    @SuppressLint("StaticFieldLeak")
    public void deleteMovieId(final int id) {
        final List<MovieResults> task = getTaskById(id);
        if (task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    database.movieDao().deleteMovie(task.get(id));
                    return null;
                }
            }.execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteMovie(final MovieResults movieResults) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.movieDao().deleteMovie(movieResults);
                return null;
            }
        }.execute();
    }

    public List<MovieResults> getTaskById(int id) {
        return database.movieDao().getMovieId(id);
    }

    public List<MovieResults> getTaskWatchlist(boolean isWatchlist) {
        return database.movieDao().getMovieWatchlist(isWatchlist);
    }

    public List<MovieResults> getTaskMovie(MovieResults movieResults) {
        return database.movieDao().getAllMovie();
    }
}
