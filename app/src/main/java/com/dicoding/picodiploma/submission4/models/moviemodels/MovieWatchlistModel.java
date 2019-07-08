package com.dicoding.picodiploma.submission4.models.moviemodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tmovieWatchlist")
public class MovieWatchlistModel implements Parcelable {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double popularity;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;

    @ColumnInfo(name = "isWatchlist")
    private boolean isWatchlist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isWatchlist() {
        return isWatchlist;
    }

    public void setWatchlist(boolean watchlist) {
        isWatchlist = watchlist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.voteAverage);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeByte(this.isWatchlist ? (byte) 1 : (byte) 0);
    }

    public MovieWatchlistModel() {
    }

    protected MovieWatchlistModel(Parcel in) {
        this.id = in.readInt();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.popularity = in.readDouble();
        this.voteCount = in.readInt();
        this.isWatchlist = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MovieWatchlistModel> CREATOR = new Parcelable.Creator<MovieWatchlistModel>() {
        @Override
        public MovieWatchlistModel createFromParcel(Parcel source) {
            return new MovieWatchlistModel(source);
        }

        @Override
        public MovieWatchlistModel[] newArray(int size) {
            return new MovieWatchlistModel[size];
        }
    };
}
