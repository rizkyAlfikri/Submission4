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
import com.dicoding.picodiploma.submission4.adapters.MovieAdapter.MovieFavoriteAdapter;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteMovieFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_MOVIE_WATCHLIST_REQUEST_CODE = 201;
    private MovieFavoriteAdapter favoriteAdapter;
    private MovieViewModel movieViewModel;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindString(R.string.snackbar_delete)
    String snackbarDelete;
    @BindString(R.string.undo)
    String undo;
    @BindString(R.string.add_watclist)
    String addWatchlist;


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favoriteAdapter = new MovieFavoriteAdapter(getActivity());
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(favoriteAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovie().observe(this, getFavoriteMovie);

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
                        MovieResults movieResults;
                        movieResults = favoriteAdapter.getMovieAtPosition(position);
                        movieViewModel.deleteSelectedMovie(movieResults);

                        Snackbar.make(view, snackbarDelete , Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.colorTicket))
                                .setAction(undo, v -> movieViewModel.insert(movieResults)).show();
                    }
                });

        touchHelper.attachToRecyclerView(rvMovie);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_MOVIE_WATCHLIST_REQUEST_CODE) {
                MovieWatchlistModel watchlistModel;
                watchlistModel = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                movieViewModel.insertWatchMovie(watchlistModel);
                Toast.makeText(getContext(), watchlistModel.getTitle() + " "  + addWatchlist,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final Observer<List<MovieResults>> getFavoriteMovie = new Observer<List<MovieResults>>() {
        @Override
        public void onChanged(List<MovieResults> movieResultsList) {
            favoriteAdapter.setFavoriteAdapter(movieResultsList);
            favoriteAdapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResultsList.get(position));
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };
}
