package com.dicoding.picodiploma.submission4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.Config;
import com.dicoding.picodiploma.submission4.viewmodel.MovieViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    private static final int ADD_MOVIE_RESULT_CODE = 101;
    private static final int ADD_MOVIE_WATCHLIST_RESULT_CODE = 201;
    private MovieResults movieResults;
    private MovieWatchlistModel watchlistModel;
    private MovieViewModel movieViewModel;

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
    TextView txtWatchlist;
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
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        movieResults = getIntent().getParcelableExtra(EXTRA_MOVIE);

        watchlistModel = initDataWatchlist(movieResults);

        txtOverview.setText(movieResults.getOverview());
        txtDate.setText(movieResults.getReleaseDate());
        txtLanguage.setText(movieResults.getOriginalLanguage());
        txtVoteAverage.setText(String.valueOf(movieResults.getVoteAverage()));
        txtVoteCount.setText(String.valueOf(movieResults.getVoteCount()));
        collapsingToolbarLayout.setTitle(movieResults.getTitle());

        String urlPhoto = Config.IMAGE_URL_BASE_PATH + movieResults.getPosterPath();
        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions())
                .into(imgPhoto);

        // Query. Cek jika data sudah ada atau belum di database
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getIdMovie(movieResults.getId()).observe(this, movieById);
        movieViewModel.getIdWatchMovie(watchlistModel.getId()).observe(this, getWatchById);
        imgWatchlist.setOnClickListener(this);
        imgFavorite.setOnClickListener(this);
    }

    private final Observer<List<MovieResults>> movieById = new Observer<List<MovieResults>>() {
        @Override
        public void onChanged(List<MovieResults> movieResultsList) {
            if (!movieResultsList.isEmpty()) {
                movieResults.setFavorite(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                txtFavorite.setText(unfavorite);

            } else {
                movieResults.setFavorite(false);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                txtFavorite.setText(favorite);
            }
        }
    };

    private final Observer<List<MovieWatchlistModel>> getWatchById
            = new Observer<List<MovieWatchlistModel>>() {
        @Override
        public void onChanged(List<MovieWatchlistModel> movieWatchlistModels) {
            if (!movieWatchlistModels.isEmpty()) {
                watchlistModel.setWatchlist(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_book_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                txtWatchlist.setText(unWatchlist);
            } else {
                watchlistModel.setWatchlist(false);
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_bookmark_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                txtWatchlist.setText(watchlist);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_favorite) {
            if (!movieResults.isFavorite()) {
                Glide.with(this)
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE, movieResults);
                setResult(ADD_MOVIE_RESULT_CODE, intent);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                Toast.makeText(this, movieResults.getTitle() + " " + removeFavorite,
                        Toast.LENGTH_SHORT).show();
                movieViewModel.deleteSelectedMovie(movieResults);
            }
        } else if (v.getId() == R.id.img_watchlist) {
            if (!watchlistModel.isWatchlist()) {
                Glide.with(getApplicationContext())
                        .load(R.drawable.ic_book_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE, watchlistModel);
                setResult(ADD_MOVIE_WATCHLIST_RESULT_CODE, intent);
            } else {
                Glide.with(this)
                        .load(R.drawable.ic_bookmark_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgWatchlist);
                Toast.makeText(this, movieResults.getTitle() + " " + removeWatchlist,
                        Toast.LENGTH_SHORT).show();
                movieViewModel.deleteSelectedWatchMovie(watchlistModel);
            }
        }
        finish();
    }

    private MovieWatchlistModel initDataWatchlist(MovieResults movieResults) {
        MovieWatchlistModel movieWatchlistModel = new MovieWatchlistModel();
        movieWatchlistModel.setTitle(movieResults.getTitle());
        movieWatchlistModel.setId(movieResults.getId());
        movieWatchlistModel.setOverview(movieResults.getOverview());
        movieWatchlistModel.setOriginalLanguage(movieResults.getOriginalLanguage());
        movieWatchlistModel.setPosterPath(movieResults.getPosterPath());
        movieWatchlistModel.setReleaseDate(movieResults.getReleaseDate());
        movieWatchlistModel.setVoteAverage(movieResults.getVoteAverage());
        movieWatchlistModel.setPopularity(movieResults.getPopularity());
        movieWatchlistModel.setVoteCount(movieResults.getVoteCount());
        return movieWatchlistModel;
    }
}


