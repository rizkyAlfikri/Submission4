package com.dicoding.picodiploma.submission4.rest;

import com.dicoding.picodiploma.submission4.utils.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitMovieService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.API_MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
