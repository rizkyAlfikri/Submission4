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
import com.dicoding.picodiploma.submission4.adapters.TvShowAdapter.TvShowWatchlistAdapter;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.TvShowViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WatchlistTvShowFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_TV_RESULT_CODE = 102;
    private TvShowWatchlistAdapter watchlistAdapter;
    private TvShowViewModel tvShowViewModel;
    @BindView(R.id.rv_tvshow)
    RecyclerView rvTvShow;
    @BindString(R.string.snackbar_delete)
    String snacknarDelete;
    @BindString(R.string.undo)
    String undo;
    @BindString(R.string.add_favorite)
    String addFavorite;


    public WatchlistTvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watchlist_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTvShow.setHasFixedSize(true);
        watchlistAdapter = new TvShowWatchlistAdapter(getActivity());
        rvTvShow.setAdapter(watchlistAdapter);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getWatchAllTvShow().observe(this, getWatchlistTv);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
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
                        TvShowWatchlistModel showWatchlistModel;
                        showWatchlistModel = watchlistAdapter.getTvShowAtPosition(position);
                        tvShowViewModel.deleteSelectedWatchTv(showWatchlistModel);

                        Snackbar.make(view, snacknarDelete, Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.colorTicket))
                                .setAction(undo, v -> tvShowViewModel.insertWatchTv(showWatchlistModel))
                        .show();
                    }
                });

        itemTouchHelper.attachToRecyclerView(rvTvShow);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_TV_RESULT_CODE ) {
                TvShowResults tvShowResults;
                tvShowResults = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV);
                tvShowViewModel.insertTv(tvShowResults);
                Toast.makeText(getContext(), tvShowResults.getName() + " " + addFavorite,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final Observer<List<TvShowWatchlistModel>> getWatchlistTv = new Observer<List<TvShowWatchlistModel>>() {
        @Override
        public void onChanged(List<TvShowWatchlistModel> tvShowWatchlistModelList) {
            watchlistAdapter.setListTv(tvShowWatchlistModelList);
            watchlistAdapter.notifyDataSetChanged();
            ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
                TvShowResults tvShowResults = initData(tvShowWatchlistModelList.get(position));
                Intent intent = new Intent(getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShowResults);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };

    private TvShowResults initData(TvShowWatchlistModel showWatchlistModel) {
        TvShowResults tvShowResults = new TvShowResults();
        tvShowResults.setId(showWatchlistModel.getId());
        tvShowResults.setFirstAirDate(showWatchlistModel.getFirstAirDate());
        tvShowResults.setOverview(showWatchlistModel.getOverview());
        tvShowResults.setOriginalLanguage(showWatchlistModel.getOriginalLanguage());
        tvShowResults.setPosterPath(showWatchlistModel.getPosterPath());
        tvShowResults.setPosterPath(showWatchlistModel.getPosterPath());
        tvShowResults.setVoteAverage(showWatchlistModel.getVoteAverage());
        tvShowResults.setName(showWatchlistModel.getName());
        tvShowResults.setVoteCount(showWatchlistModel.getVoteCount());
        return tvShowResults;
    }
}
