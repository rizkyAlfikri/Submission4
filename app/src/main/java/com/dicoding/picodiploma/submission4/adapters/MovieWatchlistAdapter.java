package com.dicoding.picodiploma.submission4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.utils.Config;
import com.dicoding.picodiploma.submission4.utils.CustomOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieWatchlistAdapter extends RecyclerView.Adapter<MovieWatchlistAdapter.MovieWatchViewHolder> {
    private List<MovieResults> listMovie;

    private Context context;

    public MovieWatchlistAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    public void setMovieWatchAdapter(List<MovieResults> listMovie) {
        this.listMovie = listMovie;
        notifyItemInserted(listMovie.size() - 1);
    }

    @NonNull
    @Override
    public MovieWatchlistAdapter.MovieWatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_watchlist, parent, false);
        return new MovieWatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieWatchlistAdapter.MovieWatchViewHolder holder, int position) {
        holder.txtTitle.setText(listMovie.get(position).getTitle());
        holder.txtDate.setText(listMovie.get(position).getReleaseDate());
        holder.txtVote.setText(String.valueOf(listMovie.get(position).getVoteAverage()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);

        Glide.with(context)
                .load(R.drawable.ic_book_black_24dp)
                .apply(new RequestOptions().override(36, 36))
                .into(holder.imgWatchlist);

        holder.imgWatchlist.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            Glide.with(context)
                    .load(R.drawable.ic_bookmark_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(holder.imgWatchlist);
            listMovie.get(position).setWatchlist(false);
        }));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieWatchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_rate)
        TextView txtVote;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindView(R.id.img_watchlist)
        ImageView imgWatchlist;
        MovieWatchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
