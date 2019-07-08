package com.dicoding.picodiploma.submission4.adapters.MovieAdapter;

import android.content.Context;
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
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.utils.Config;
import com.dicoding.picodiploma.submission4.utils.CustomOnItemClickListener;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieResults> listMovie;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(List<MovieResults> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.txtTitle.setText(listMovie.get(position).getTitle());
        holder.txtDate.setText(listMovie.get(position).getReleaseDate());
        holder.txtRate.setText(String.valueOf(listMovie.get(position).getVoteAverage()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);

        holder.txtTicket.setOnClickListener(
                new CustomOnItemClickListener(position, (view, position1) ->
                        Toast.makeText(context, holder.comingSoon,
                                Toast.LENGTH_SHORT).show()));
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
            return listMovie.size();
        } else {
            return 0;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_rate)
        TextView txtRate;
        @BindView(R.id.txt_ticket)
        TextView txtTicket;
        @BindString(R.string.coming_soon)
        String comingSoon;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
