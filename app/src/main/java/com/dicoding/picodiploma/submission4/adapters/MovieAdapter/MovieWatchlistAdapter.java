package com.dicoding.picodiploma.submission4.adapters.MovieAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.submission4.R;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieWatchlistAdapter extends RecyclerView.Adapter<MovieWatchlistAdapter.WatchlistViewHolder> {
    private Activity activity;
    private List<MovieWatchlistModel> listMovie;

    public MovieWatchlistAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setWatchlistModels(List<MovieWatchlistModel> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    public MovieWatchlistModel getMoviePosition(int position) {
        return listMovie.get(position);
    }

    @NonNull
    @Override
    public MovieWatchlistAdapter.WatchlistViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_movie_favorite, parent, false);
        return new WatchlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieWatchlistAdapter.WatchlistViewHolder holder, int position) {
        holder.txtTitle.setText(listMovie.get(position).getTitle());
        holder.txtDate.setText(listMovie.get(position).getReleaseDate());
        holder.txtRate.setText(String.valueOf(listMovie.get(position).getVoteAverage()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listMovie.get(position).getPosterPath();

        Glide.with(activity)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);
        holder.txtTicket.setOnClickListener(v -> Toast.makeText(activity, holder.comingSoon,
                Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
            return listMovie.size();
        } else {
            return 0;
        }
    }

    class WatchlistViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_rate)
        TextView txtRate;
        @BindView(R.id.txt_ticket)
        TextView txtTicket;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindString(R.string.coming_soon)
        String comingSoon;

        WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
