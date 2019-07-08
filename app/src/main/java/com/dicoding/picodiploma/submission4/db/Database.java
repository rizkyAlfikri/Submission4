package com.dicoding.picodiploma.submission4.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dicoding.picodiploma.submission4.db.moviedb.MovieDao;
import com.dicoding.picodiploma.submission4.db.moviedb.MovieWatchDao;
import com.dicoding.picodiploma.submission4.db.tvshowdb.TvShowDao;
import com.dicoding.picodiploma.submission4.db.tvshowdb.TvShowWatchDao;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieResults;
import com.dicoding.picodiploma.submission4.models.moviemodels.MovieWatchlistModel;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowResults;
import com.dicoding.picodiploma.submission4.models.tvshowmodels.TvShowWatchlistModel;
import com.dicoding.picodiploma.submission4.utils.Config;


@androidx.room.Database(entities = {MovieResults.class, MovieWatchlistModel.class, TvShowResults.class,
        TvShowWatchlistModel.class}, version = 1)

public abstract class Database extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract MovieWatchDao movieWatchDao();
    public abstract TvShowDao tvShowDao();
    public abstract TvShowWatchDao tvShowWatchDao();

    private static Database INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // kita tidak mengubah struktur table, sehingga kita tidak memasukan statement disini
            // method ini berfungsi untuk menjaga data, sehingga kita mengimplementasikan migrasi database
            // tetapi tidak mengubah struktur table. sehingga kita cukup menyediakan method untuk
            // migrasinya yang kosong
        }
    };

    public static Database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, Config.DB_MOVIE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

