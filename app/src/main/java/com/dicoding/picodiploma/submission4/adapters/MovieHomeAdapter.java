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
import com.dicoding.picodiploma.submission4.db.MovieRepository;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.utils.Config;
import com.dicoding.picodiploma.submission4.utils.CustomOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieHomeAdapter extends RecyclerView.Adapter<MovieHomeAdapter.MovieHomeViewHolder> {
    private List<MovieResults> listMovie;
    private Context context;
    private boolean isWatchlist = false;
    private boolean isFavorite = false;

    public MovieHomeAdapter(List<MovieResults> listMovie, Context context) {
        this.listMovie = listMovie;
        this.context = context;
        notifyDataSetChanged();
    }

    public void setFavorite(boolean isWatchlist) {
        this.isWatchlist = isWatchlist;
    }

    @NonNull
    @Override
    public MovieHomeAdapter.MovieHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHomeAdapter.MovieHomeViewHolder holder, int position) {

        holder.txtTitle.setText(listMovie.get(position).getTitle());
        holder.txtVote.setText(String.valueOf(listMovie.get(position).getVoteAverage()));

        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(urlPhoto)
                .apply(new RequestOptions().override(120, 180))
                .into(holder.imgPhoto);

        isFavorite = listMovie.get(position).getIsFavorite();
        isWatchlist = listMovie.get(position).getIsWatchlist();

        if (!isWatchlist) {
            Glide.with(context)
                    .load(R.drawable.ic_bookmark_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(holder.imgWatch);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_book_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(holder.imgWatch);
        }

        if (!isFavorite) {
            Glide.with(context)
                    .load(R.drawable.ic_favorite_border_black_24dp)
                    .apply(new RequestOptions().override(24, 24))
                    .into(holder.imgFavorite);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_favorite_black_24dp)
                    .apply(new RequestOptions().override(24, 24))
                    .into(holder.imgFavorite);
        }

        holder.imgWatch.setOnClickListener(new CustomOnItemClickListener(position, (view, position1) -> {
            if (!isWatchlist) {
                Glide.with(context)
                        .load(R.drawable.ic_book_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(holder.imgWatch);
                isWatchlist = true;
                listMovie.get(position).setWatchlist(isWatchlist);
            } else {
                Glide.with(context).load(R.drawable.ic_bookmark_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(holder.imgWatch);
                isWatchlist = false;
                listMovie.get(position).setWatchlist(isWatchlist);
            }
        }));

        holder.imgFavorite.setOnClickListener(new CustomOnItemClickListener(position, (view, position12) -> {
            if (!isFavorite) {
                Glide.with(context)
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(24, 24))
                        .into(holder.imgFavorite);
                isFavorite = true;
                listMovie.get(position).setFavorite(isFavorite);
            } else {
                Glide.with(context)
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(24, 24))
                        .into(holder.imgFavorite);
                isFavorite = false;
                listMovie.get(position).setFavorite(isFavorite);
            }
        }));

        if (isWatchlist) {
            holder.movieRepository.insertedTask(listMovie.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MovieHomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_vote)
        TextView txtVote;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindView(R.id.img_watchlist)
        ImageView imgWatch;
        @BindView(R.id.img_favorite)
        ImageView imgFavorite;
        private MovieRepository movieRepository = new MovieRepository(context);

        MovieHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
