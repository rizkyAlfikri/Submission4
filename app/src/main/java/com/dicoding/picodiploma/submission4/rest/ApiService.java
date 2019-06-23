package com.dicoding.picodiploma.submission4.rest;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie")
    Call<MovieResponse> getMovie(@Query("api_key") String apiKey);
}
