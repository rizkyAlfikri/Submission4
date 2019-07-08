package com.dicoding.picodiploma.submission4.adapters.TvShowAdapter;

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
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.utils.Config;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TvShowHomeAdapter extends RecyclerView.Adapter<TvShowHomeAdapter.TvHomeViewHolder> {
    private Context context;
    private List<TvShowResults> listTv;

    public TvShowHomeAdapter(Context context) {
        this.context = context;
    }

    public void setTvShowAdapter(List<TvShowResults> listTv) {
        this.listTv = listTv;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowHomeAdapter.TvHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new TvHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowHomeAdapter.TvHomeViewHolder holder, int position) {
        holder.txtTitle.setText(listTv.get(position).getName());
        holder.txtVote.setText(String.valueOf(listTv.get(position).getVoteAverage()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listTv.get(position).getPosterPath();
        Glide.with(context)
                .load(urlPhoto)
                .apply(new RequestOptions().override(120, 180))
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        if (listTv != null) {
            return listTv.size();
        } else {
            return 0;
        }
    }

    class TvHomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_vote)
        TextView txtVote;

        TvHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
