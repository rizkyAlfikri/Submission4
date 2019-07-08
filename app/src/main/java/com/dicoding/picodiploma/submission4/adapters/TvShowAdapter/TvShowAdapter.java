package com.dicoding.picodiploma.submission4.adapters.TvShowAdapter;

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
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private Activity activity;
    private List<TvShowResults> listTv;

    public TvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvShowAdapter(List<TvShowResults> listTv) {
        this.listTv = listTv;
    }

    @NonNull
    @Override
    public TvShowAdapter.TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_tvshow, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvShowViewHolder holder, int position) {
        holder.txtTitle.setText(listTv.get(position).getName());
        holder.txtDate.setText(listTv.get(position).getFirstAirDate());
        holder.txtRate.setText(String.valueOf(listTv.get(position).getVoteAverage()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listTv.get(position).getPosterPath();
        Glide.with(activity)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);

        holder.txtTicket.setOnClickListener(v -> Toast.makeText(activity, holder.comingSoon,
                Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        if (listTv != null) {
            return listTv.size();
        } else
            return 0;
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
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

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
