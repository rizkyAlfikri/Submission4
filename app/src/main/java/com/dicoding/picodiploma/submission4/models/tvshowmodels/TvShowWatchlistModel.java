package com.dicoding.picodiploma.submission4.models.tvshowmodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ttshowWatchlist")
public class TvShowWatchlistModel implements Parcelable {
    @PrimaryKey
    @ColumnInfo
    @SerializedName("id")
    private int id;

    @ColumnInfo
    @SerializedName("first_air_date")
    private String firstAirDate;

    @ColumnInfo
    @SerializedName("overview")
    private String overview;

    @ColumnInfo
    @SerializedName("original_language")
    private String originalLanguage;

    @ColumnInfo
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo
    @SerializedName("popularity")
    private double popularity;

    @ColumnInfo
    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo
    @SerializedName("name")
    private String name;

    @ColumnInfo
    @SerializedName("vote_count")
    private int voteCount;

    @ColumnInfo(name = "watchlist")
    private boolean watchlist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.posterPath);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.name);
        dest.writeInt(this.voteCount);
        dest.writeByte(this.watchlist ? (byte) 1 : (byte) 0);
    }

    public TvShowWatchlistModel() {
    }

    protected TvShowWatchlistModel(Parcel in) {
        this.id = in.readInt();
        this.firstAirDate = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
        this.name = in.readString();
        this.voteCount = in.readInt();
        this.watchlist = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TvShowWatchlistModel> CREATOR = new Parcelable.Creator<TvShowWatchlistModel>() {
        @Override
        public TvShowWatchlistModel createFromParcel(Parcel source) {
            return new TvShowWatchlistModel(source);
        }

        @Override
        public TvShowWatchlistModel[] newArray(int size) {
            return new TvShowWatchlistModel[size];
        }
    };
}
