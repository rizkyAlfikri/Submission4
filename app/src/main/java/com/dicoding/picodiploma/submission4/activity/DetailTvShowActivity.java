package com.dicoding.picodiploma.submission4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.Config;
import com.dicoding.picodiploma.submission4.viewmodel.TvShowViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvShowActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TV = "extra_tv";
    private static final int ADD_TV_RESULT_CODE = 102;
    private static final int ADD_TV_WATCHLIST_RESULT_CODE = 202;
    private TvShowResults tvShowResults;
    private TvShowWatchlistModel showWatchlistModel;
    private TvShowViewModel tvShowViewModel;

    @BindView(R.id.txt_overview)
    TextView txtOverview;
    @BindView(R.id.txt_vote_average)
    TextView txtVoteAverage;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_language)
    TextView txtLanguage;
    @BindView(R.id.txt_vote_count)
    TextView txtVoteCount;
    @BindView(R.id.txt_favorite)
    TextView txtFavorite;
    @BindView(R.id.txt_watchlist)
    TextView txtWacthlist;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;
    @BindView(R.id.img_favorite)
    ImageView imgFavorite;
    @BindView(R.id.img_watchlist)
    ImageView imgWatchlist;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindString(R.string.remove_favorite)
    String removeFavorite;
    @BindString(R.string.remove_watchlist)
    String removeWatchlist;
    @BindString(R.string.favorite)
    String favorite;
    @BindString(R.string.unfavorite)
    String unfavorite;
    @BindString(R.string.watchlist)
    String watchlist;
    @BindString(R.string.unWatchlist)
    String unWatchlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        ButterKnife.bind(this);

        tvShowResults = getIntent().getParcelableExtra(EXTRA_TV);
        showWatchlistModel = initDataWatchlist(tvShowResults);

        txtOverview.setText(tvShowResults.getOverview());
        txtDate.setText(tvShowResults.getFirstAirDate());
        txtLanguage.setText(tvShowResults.getOriginalLanguage());
        txtVoteAverage.setText(String.valueOf(tvShowResults.getVoteAverage()));
        txtVoteCount.setText(String.valueOf(tvShowResults.getVoteCount()));
        collapsingToolbarLayout.setTitle(tvShowResults.getName());
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + tvShowResults.getPosterPath();
        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions())
                .into(imgPhoto);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getIdTv(tvShowResults.getId()).observe(this, getIdTv);
        tvShowViewModel.getWatchIdTvShow(showWatchlistModel.getId()).observe(this, getWatchById);

        imgFavorite.setOnClickListener(this);
        imgWatchlist.setOnClickListener(this);
    }

    private final Observer<List<TvShowResults>> getIdTv = new Observer<List<TvShowResults>>() {
        @Override
        public void onChanged(List<TvShowResults> tvShowResultsList) {
            if (!tvShowResultsList.isEmpty()) {
                tvShowResults.setFavorite(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                txtFavorite.setText(unfavorite);
            } else {
                tvShowResults.setFavorite(false);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                txtFavorite.setText(favorite);
            }
        }
    };

    private final Observer<List<TvShowWatchlistModel>> getWatchById =
            new Observer<List<TvShowWatchlistModel>>() {
        @Override
        public void onChanged(List<TvShowWatchlistModel> tvShowWatchlistModelList) {
            if (!tvShowWatchlistModelList.isEmpty()) {
                showWatchlistModel.setWatchlist(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_book_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                txtWacthlist.setText(unWatchlist);
            } else {
                showWatchlistModel.setWatchlist(false);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_bookmark_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                txtWacthlist.setText(watchlist);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_favorite) {
            if (!tvShowResults.isFavorite()) {
                Glide.with(this)
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_TV, tvShowResults);
                setResult(ADD_TV_RESULT_CODE, intent);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                Toast.makeText(this, tvShowResults.getName() + " " + removeFavorite,
                        Toast.LENGTH_SHORT).show();
                tvShowViewModel.deleteSelectedTv(tvShowResults);
            }
        } else if (v.getId() == R.id.img_watchlist) {
            if (!showWatchlistModel.isWatchlist()) {
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_book_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_TV, showWatchlistModel);
                setResult(ADD_TV_WATCHLIST_RESULT_CODE, intent);
            } else {
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_bookmark_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                Toast.makeText(this, showWatchlistModel.getName() +  " " + removeWatchlist,
                        Toast.LENGTH_SHORT).show();
                tvShowViewModel.deleteSelectedWatchTv(showWatchlistModel);
            }
        }
        finish();
    }

    private TvShowWatchlistModel initDataWatchlist(TvShowResults tvShowResults) {
        TvShowWatchlistModel showWatchlistModel = new TvShowWatchlistModel();
        showWatchlistModel.setId(tvShowResults.getId());
        showWatchlistModel.setFirstAirDate(tvShowResults.getFirstAirDate());
        showWatchlistModel.setOverview(tvShowResults.getOverview());
        showWatchlistModel.setOriginalLanguage(tvShowResults.getOriginalLanguage());
        showWatchlistModel.setPosterPath(tvShowResults.getPosterPath());
        showWatchlistModel.setName(tvShowResults.getName());
        showWatchlistModel.setVoteAverage(tvShowResults.getVoteAverage());
        showWatchlistModel.setPopularity(tvShowResults.getPopularity());
        showWatchlistModel.setVoteCount(tvShowResults.getVoteCount());
        return showWatchlistModel;
    }
}
