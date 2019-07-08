package com.dicoding.picodiploma.submission4.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.activity.DetailMovieActivity;
import com.dicoding.picodiploma.submission4.activity.DetailTvShowActivity;
import com.dicoding.picodiploma.submission4.adapters.MovieAdapter.MovieHomeAdapter;
import com.dicoding.picodiploma.submission4.adapters.TvShowAdapter.TvShowHomeAdapter;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.MovieViewModel;
import com.dicoding.picodiploma.submission4.viewmodel.TvShowViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_MOVIE_RESULT_CODE = 101;
    private static final int ADD_TV_RESULT_CODE = 102;
    private static final int ADD_MOVIE_WATCHLIST_RESULT_CODE = 201;
    private static final int ADD_TV_WATCHLIST_RESULT_CODE = 202;
    private MovieHomeAdapter movieHomeAdapter;
    private MovieViewModel movieViewModel;
    private TvShowHomeAdapter tvShowHomeAdapter;
    private TvShowViewModel tvShowViewModel;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.rv_tvshow)
    RecyclerView rvTvShow;
    @BindString(R.string.add_favorite)
    String addFavorite;
    @BindString(R.string.add_watclist)
    String addWatchlist;
    @BindView(R.id.progress_bar1)
    ProgressBar progressBar1;
    @BindView(R.id.progress_bar2)
    ProgressBar progressBar2;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);

        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, view.isInLayout()));

        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL, view.isInLayout()));

        rvMovie.setHasFixedSize(true);
        rvTvShow.setHasFixedSize(true);

        movieHomeAdapter = new MovieHomeAdapter(getContext());
        tvShowHomeAdapter = new TvShowHomeAdapter(getContext());

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieData().observe(this, getMovieData);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvData().observe(this, getTvData);
    }

    private final Observer<List<MovieResults>> getMovieData = new Observer<List<MovieResults>>() {
        @Override
        public void onChanged(List<MovieResults> movieResults) {
            movieHomeAdapter.setAdapterMovie(movieResults);
            rvMovie.setAdapter(movieHomeAdapter);
            movieHomeAdapter.notifyDataSetChanged();
            progressBar1.setVisibility(View.GONE);
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent movieIntent = new Intent(recyclerView.getContext(), DetailMovieActivity.class);
                movieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResults.get(position));
                startActivityForResult(movieIntent, ADD_REQUEST_CODE);
            });
        }
    };

    private final Observer<List<TvShowResults>> getTvData = new Observer<List<TvShowResults>>() {
        @Override
        public void onChanged(List<TvShowResults> tvShowResults) {
            tvShowHomeAdapter.setTvShowAdapter(tvShowResults);
            rvTvShow.setAdapter(tvShowHomeAdapter);
            tvShowHomeAdapter.notifyDataSetChanged();
            progressBar2.setVisibility(View.GONE);
            ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
                Intent tvIntent = new Intent(recyclerView.getContext(), DetailTvShowActivity.class);
                tvIntent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShowResults.get(position));
                startActivityForResult(tvIntent, ADD_REQUEST_CODE);
            });
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_MOVIE_RESULT_CODE) {
                MovieResults movieResults = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                movieViewModel.insert(movieResults);
                Toast.makeText(getContext(), movieResults.getTitle() + " " +  addFavorite,
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == ADD_TV_RESULT_CODE) {
                TvShowResults tvShowResults = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV);
                tvShowViewModel.insertTv(tvShowResults);
                Toast.makeText(getContext(), tvShowResults.getName() + " " + addFavorite,
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == ADD_MOVIE_WATCHLIST_RESULT_CODE) {
                MovieWatchlistModel watchlistModel = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                movieViewModel.insertWatchMovie(watchlistModel);
                Toast.makeText(getContext(), watchlistModel.getTitle() + " " + addWatchlist ,
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == ADD_TV_WATCHLIST_RESULT_CODE) {
                TvShowWatchlistModel showWatchlistModel = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV);
                tvShowViewModel.insertWatchTv(showWatchlistModel);
                Toast.makeText(getContext(), showWatchlistModel.getName() + " " + addWatchlist, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
