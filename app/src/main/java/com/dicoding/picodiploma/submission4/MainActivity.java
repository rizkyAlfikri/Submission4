package com.dicoding.picodiploma.submission4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dicoding.picodiploma.submission4.fragments.HomeFragment;
import com.dicoding.picodiploma.submission4.fragments.MyAlertDialogFragment;
import com.dicoding.picodiploma.submission4.fragments.MyTvAlertDialogFragment;
import com.dicoding.picodiploma.submission4.fragments.moviefragments.FavoriteMovieFragment;
import com.dicoding.picodiploma.submission4.fragments.moviefragments.MovieFragment;
import com.dicoding.picodiploma.submission4.fragments.moviefragments.WatchlistMovieFragment;
import com.dicoding.picodiploma.submission4.fragments.tvshowfragments.FavoriteTvShowFragment;
import com.dicoding.picodiploma.submission4.fragments.tvshowfragments.TvShowFragment;
import com.dicoding.picodiploma.submission4.fragments.tvshowfragments.WatchlistTvShowFragment;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindString(R.string.dialog_title)
    String dialogTile;
    @BindString(R.string.yes)
    String yes;
    @BindString(R.string.no)
    String no;
    @BindString(R.string.menu_home)
    String home;
    @BindString(R.string.movie)
    String movie;
    @BindString(R.string.tv_show)
    String tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitle(home);
            getSupportActionBar().setTitle(home);
        }

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView;
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.content_main, currentFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_change_settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.clear_all_movie:
                showAlertDialog();
                break;
            case R.id.clear_all_tv:
                showTvAlertDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = "";

        if (id == R.id.nav_home) {
            title = home;
            fragment = new HomeFragment();
        } else if (id == R.id.nav_movie) {
            title = "";
            fragment = new MovieFragment();
        } else if (id == R.id.nav_tvshow) {
            title = "";
            fragment = new TvShowFragment();
        } else if (id == R.id.nav_movie_favorite) {
            title = movie;
            fragment = new FavoriteMovieFragment();
        } else if (id == R.id.nav_tvshow_favorite) {
            title = tvShow;
            fragment = new FavoriteTvShowFragment();
        } else if (id == R.id.nav_movie_watchlist) {
            title = movie;
            fragment = new WatchlistMovieFragment();
        } else if (id == R.id.nav_tvshow_watchlist) {
            title = tvShow;
            fragment = new WatchlistTvShowFragment();
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }
        if (getSupportActionBar() != null) {
            toolbar.setTitle(title);
            getSupportActionBar().setTitle(title);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void showAlertDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyAlertDialogFragment alertDialogFragment = MyAlertDialogFragment.dialogFragment(dialogTile);
        alertDialogFragment.show(fragmentManager, dialogTile);
    }

    private void showTvAlertDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyTvAlertDialogFragment tvAlertDialogFragment = MyTvAlertDialogFragment.dialogFragment(dialogTile);
        tvAlertDialogFragment.show(fragmentManager, dialogTile);
    }
}
