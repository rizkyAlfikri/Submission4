package com.dicoding.picodiploma.submission4.ui;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.adapters.MovieWatchlistAdapter;
import com.dicoding.picodiploma.submission4.db.MovieRepository;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchlistFragment extends Fragment {
    private MovieWatchlistAdapter watchlistAdapter;
    ArrayList<MovieResults> movieResults = new ArrayList<>();
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    public WatchlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        watchlistAdapter = new MovieWatchlistAdapter(getContext());
        watchlistAdapter.notifyDataSetChanged();
        rvMovie.setAdapter(watchlistAdapter);

        MovieRepository movieRepository = new MovieRepository(getContext());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                movieResults.addAll(movieRepository.getTaskWatchlist(true));
                return null;
            }
        }.execute();

        watchlistAdapter.setMovieWatchAdapter(movieResults);
        watchlistAdapter.notifyDataSetChanged();

    }


}
