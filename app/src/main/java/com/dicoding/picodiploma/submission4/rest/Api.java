package com.dicoding.picodiploma.submission4.rest;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResponse;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {
    @GET("movie")
    Call<MovieResponse> getMovie(@Query("api_key") String apiKey);

    @GET("tv")
    Call<TvShowResponse> getTvShow(@Query("api_key") String apiKey);
}
