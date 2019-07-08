package com.dicoding.picodiploma.submission4.fragments.tvshowfragments;


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
import com.dicoding.picodiploma.submission4.activity.DetailTvShowActivity;
import com.dicoding.picodiploma.submission4.adapters.TvShowAdapter.TvShowFavoriteAdapter;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.TvShowViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteTvShowFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_TV_WATCHLIST_RESULT_CODE = 202;
    private TvShowFavoriteAdapter favoriteAdapter;
    private TvShowViewModel tvShowViewModel;
    @BindView(R.id.rv_tvshow)
    RecyclerView rvTvShow;
    @BindString(R.string.snackbar_delete)
    String snackbarDelete;
    @BindString(R.string.undo)
    String undo;
    @BindString(R.string.add_watclist)
    String addWatchlist;


    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favoriteAdapter = new TvShowFavoriteAdapter(getActivity());
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setAdapter(favoriteAdapter);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getAllTv().observe(this, getFavoriteTv);

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView
                            , @NonNull RecyclerView.ViewHolder viewHolder
                            , @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        TvShowResults tvShowResults;
                        tvShowResults = favoriteAdapter.getTvShowAtPosition(position);
                        tvShowViewModel.deleteSelectedTv(tvShowResults);

                        Snackbar.make(view, snackbarDelete, Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.colorTicket))
                                .setAction(undo, v -> tvShowViewModel.insertTv(tvShowResults)).show();
                    }
                });

        touchHelper.attachToRecyclerView(rvTvShow);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_TV_WATCHLIST_RESULT_CODE) {
                TvShowWatchlistModel showWatchlistModel;
                showWatchlistModel = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV);
                tvShowViewModel.insertWatchTv(showWatchlistModel);
                Toast.makeText(getContext(), showWatchlistModel.getName() +  " " + addWatchlist,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final Observer<List<TvShowResults>> getFavoriteTv = new Observer<List<TvShowResults>>() {
        @Override
        public void onChanged(List<TvShowResults> tvShowResultsList) {
            favoriteAdapter.setTvFavoriteAdapter(tvShowResultsList);
            favoriteAdapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShowResultsList.get(position));
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };
}
