package com.dicoding.picodiploma.submission4.fragments.tvshowfragments;


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
import com.dicoding.picodiploma.submission4.activity.DetailTvShowActivity;
import com.dicoding.picodiploma.submission4.adapters.TvShowAdapter.TvShowAdapter;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.ItemClickSupport;
import com.dicoding.picodiploma.submission4.viewmodel.TvShowViewModel;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TvShowFragment extends Fragment {
    private static final int ADD_REQUEST_CODE = 1;
    private static final int ADD_TV_RESULT_CODE = 102;
    private static final int ADD_TV_WATCHLIST_RESULT_CODE = 202;
    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel tvShowViewModel;
    @BindView(R.id.progress_bar2)
    ProgressBar progressBar;
    @BindView(R.id.rv_tvshow)
    RecyclerView rvTvShow;
    @BindString(R.string.add_favorite)
    String addFavorite;
    @BindString(R.string.add_watclist)
    String addWatchlist;


    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTvShow.setHasFixedSize(true);
        tvShowAdapter = new TvShowAdapter(getActivity());
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvData().observe(this, getTvData);
    }

    private final Observer<List<TvShowResults>> getTvData = new Observer<List<TvShowResults>>() {
        @Override
        public void onChanged(List<TvShowResults> tvShowResults) {
            tvShowAdapter.setTvShowAdapter(tvShowResults);
            tvShowAdapter.notifyDataSetChanged();
            rvTvShow.setAdapter(tvShowAdapter);
            progressBar.setVisibility(View.GONE);

            ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShowResults.get(position));
                startActivityForResult(intent, ADD_REQUEST_CODE);
            });
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == ADD_TV_RESULT_CODE) {
                TvShowResults tvShowResults = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV);
                tvShowViewModel.insertTv(tvShowResults);
                Toast.makeText(getContext(), tvShowResults.getName() + " " + addFavorite,
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == ADD_TV_WATCHLIST_RESULT_CODE) {
                TvShowWatchlistModel showWatchlistModel = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TV);
                tvShowViewModel.insertWatchTv(showWatchlistModel);
                Toast.makeText(getContext(), showWatchlistModel.getName() +  " " + addWatchlist,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
