package com.dicoding.picodiploma.submission4.fragments.moviefragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.activity.DetailMovieActivity;
import com.dicoding.picodiploma.submission4.adapters.MovieAdapter.MovieWatchlistAdapter;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WatchlistMovieFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_MOVIE_RESULT_CODE = 101;
    private MovieViewModel movieViewModel;
    private MovieWatchlistAdapter watchlistAdapter;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindString(R.string.snackbar_delete)
    String snackbarDelete;
    @BindString(R.string.undo)
    String undo;
    @BindString(R.string.add_favorite)
    String addFavorite;


    public WatchlistMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watchlist_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        watchlistAdapter = new MovieWatchlistAdapter(getActivity());
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(watchlistAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllWatchMovie().observe(this, getWatchlistMovie);

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        MovieWatchlistModel watchlistModel;
                        watchlistModel = watchlistAdapter.getMoviePosition(position);
                        movieViewModel.deleteSelectedWatchMovie(watchlistModel);

                        Snackbar.make(view, snackbarDelete, Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.colorTicket))
                                .setAction(undo, v -> movieViewModel.insertWatchMovie(watchlistModel)).show();
                    }
                });

        touchHelper.attachToRecyclerView(rvMovie);
    }

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
            }
        }
    }

    private final Observer<List<MovieWatchlistModel>> getWatchlistMovie = new Observer<List<MovieWatchlistModel>>() {
        @Override
        public void onChanged(List<MovieWatchlistModel> movieWatchlistModels) {
            watchlistAdapter.setWatchlistModels(movieWatchlistModels);
            watchlistAdapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                MovieResults movieResults = initData(movieWatchlistModels.get(position));
                Intent intent = new Intent(recyclerView.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResults);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };

    private MovieResults initData(MovieWatchlistModel movieWatchlistModel) {
        MovieResults movieResults = new MovieResults();
        movieResults.setId(movieWatchlistModel.getId());
        movieResults.setOverview(movieWatchlistModel.getOverview());
        movieResults.setOriginalLanguage(movieWatchlistModel.getOriginalLanguage());
        movieResults.setTitle(movieWatchlistModel.getTitle());
        movieResults.setPopularity(movieWatchlistModel.getPopularity());
        movieResults.setPosterPath(movieWatchlistModel.getPosterPath());
        movieResults.setReleaseDate(movieWatchlistModel.getReleaseDate());
        movieResults.setVoteAverage(movieWatchlistModel.getVoteAverage());
        movieResults.setVoteCount(movieWatchlistModel.getVoteCount());
        return movieResults;
    }
}
