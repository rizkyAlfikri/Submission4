package com.dicoding.picodiploma.submission4.fragments.moviefragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.activity.DetailMovieActivity;
import com.dicoding.picodiploma.submission4.adapters.MovieAdapter.MovieAdapter;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.MovieViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_MOVIE_RESULT_CODE = 101;
    private static final int ADD_MOVIE_WATCHLIST_REQUEST_CODE = 201;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindString(R.string.add_favorite)
    String addFavorite;
    @BindString(R.string.add_watclist)
    String addWatchlist;
    @BindView(R.id.progress_bar1)
    ProgressBar progressBar1;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        progressBar1.setVisibility(View.VISIBLE);
        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovie.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(view.getContext());

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieData().observe(this, getMovie);
    }

    private final Observer<List<MovieResults>> getMovie = new Observer<List<MovieResults>>() {
        @Override
        public void onChanged(List<MovieResults> movieResults) {
            movieAdapter.setListMovie(movieResults);
            rvMovie.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();
            progressBar1.setVisibility(View.GONE);
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent movieIntent = new Intent(getActivity(), DetailMovieActivity.class);
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResults.get(position));
                startActivityForResult(movieIntent, ADD_REQUEST_CODE);
            });
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_MOVIE_RESULT_CODE) {
                MovieResults movieResults;
                movieResults = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                movieViewModel.insert(movieResults);
                Toast.makeText(getContext(), movieResults.getTitle() + " " + addFavorite
                        , Toast.LENGTH_SHORT).show();
            } else if (resultCode == ADD_MOVIE_WATCHLIST_REQUEST_CODE) {
                MovieWatchlistModel watchlistModel;
                watchlistModel = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                movieViewModel.insertWatchMovie(watchlistModel);
                Toast.makeText(getContext(), watchlistModel.getTitle() + " "  + addWatchlist,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
