package com.dicoding.picodiploma.submission4;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(ArrayList<MovieResults> movieResults);
}
