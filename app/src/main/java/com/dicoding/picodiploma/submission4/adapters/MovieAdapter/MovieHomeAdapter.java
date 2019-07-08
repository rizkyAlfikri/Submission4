package com.dicoding.picodiploma.submission4.adapters.MovieAdapter;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieHomeAdapter extends RecyclerView.Adapter<MovieHomeAdapter.MovieHomeViewHolder> {
    private List<MovieResults> listMovie;
    private Context context;

    public MovieHomeAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterMovie(List<MovieResults> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHomeAdapter.MovieHomeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
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
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
            return listMovie.size();
        } else {
            return 0;
        }
    }

    class MovieHomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_vote)
        TextView txtVote;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;

        MovieHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
