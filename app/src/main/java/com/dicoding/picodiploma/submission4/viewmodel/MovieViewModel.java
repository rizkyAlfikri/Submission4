package com.dicoding.picodiploma.submission4.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResponse;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.rest.ApiService;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<MovieResults>> listMovie;

    public LiveData<List<MovieResults>> getListMovie() {
        if (listMovie == null) {
            listMovie = new MutableLiveData<>();

            loadMovie();
        }
        return listMovie;
    }

    private void loadMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<MovieResponse> call = apiService.getMovie(Config.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                listMovie.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Failure", t.getMessage());
            }
        });
    }
}
