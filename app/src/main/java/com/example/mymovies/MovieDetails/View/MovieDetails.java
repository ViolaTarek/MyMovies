package com.example.mymovies.MovieDetails.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.Favourites.FavoriteDb;
import com.example.mymovies.Favourites.FavoriteViewModel;
import com.example.mymovies.Helper.ApplicationExecuter;
import com.example.mymovies.Helper.GlideApp;
import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.MovieDetails.Presenter.MoviePresenter;
import com.example.mymovies.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetails extends AppCompatActivity implements IMovieView {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.image_movie_backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.text_title)
    TextView title;
    @BindView(R.id.image_poster)
    ImageView poster;
    @BindView(R.id.chip_group)
    ChipGroup chipGroup;
    @BindView(R.id.text_vote)
    TextView vote;
    @BindView(R.id.label_vote)
    TextView labelVotes;
    @BindView(R.id.text_overview)
    TextView overview;
    @BindView(R.id.text_release_date)
    TextView releaseDate;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    int id;
    MovieModel movie;
    private FavoriteDb mDatabase;
    private FavoriteViewModel mFavVMod;
    boolean checkMovieInFavorites;

    MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        id = getIntent().getIntExtra("id", -1);
        presenter = new MoviePresenter(this);
        presenter.getMovie(id);
        mDatabase = FavoriteDb.getInstance(getApplicationContext());
        mFavVMod = ViewModelProviders.of(this).get(FavoriteViewModel.class);

    }

    public void initView() {
        String backdropPath = "https://image.tmdb.org/t/p/w500" + movie.getBackdrop_path();
        String posterPath = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        GlideApp.with(this)
                .load(backdropPath)
                .into(backdrop);
        GlideApp.with(this)
                .load(posterPath)
                .into(poster);
        title.setText(movie.getMovie_original_Title());
        initChipView();
        vote.setText(Double.toString(movie.getVoteAverage()));
        labelVotes.setText(Integer.toString(movie.getVoteCount()) + " votes");
        releaseDate.setText(movie.getDate_of_release());
        overview.setText(movie.getOverview());
        progressBar.setVisibility(View.GONE);
    }

    public void initChipView() {
        if (movie.getGenres() != null) {
            for (int i = 0; i < movie.getGenres().size(); i++) {
                Chip chip = new Chip(this);
                chip.setText(movie.getGenres().get(i).getGenreName());
                chip.setChipBackgroundColorResource(R.color.light_grey_hint);
                chip.setCloseIconVisible(false);
                chip.setTextColor(getResources().getColor(R.color.colorPrimary));
                chipGroup.addView(chip);
            }
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            handleCollapsedToolbarTitle();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleCollapsedToolbarTitle() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(movie.getMovie_original_Title());
                    isShow = true;
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onSuccess(MovieModel movieModel) {
        progressBar.setVisibility(View.GONE);
        movie = movieModel;
        checkIfExists flag = new checkIfExists();
        flag.execute();
        setupToolbar();
        initView();
    }
    @OnClick(R.id.fab)
    public void Favorite(){
        if(checkMovieInFavorites){
            ApplicationExecuter.getsInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDatabase.favoritesDao().deleteThisMovie(movie.getMovie_id());

                }
            });
            fab.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));

            checkMovieInFavorites = false;
            Toast.makeText(getApplicationContext(), "Deleted " + movie.getMovie_original_Title() + " from Favorites", Toast.LENGTH_SHORT).show();
        } else {
            mFavVMod.insert(movie);
            fab.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

            checkMovieInFavorites = true;
            Toast.makeText(getApplicationContext(), "Added " + movie.getMovie_original_Title() + " to Favorites", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("StaticFieldLeak")
    protected class checkIfExists extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            Boolean checkResult;
            Integer checkMovieId = mDatabase.favoritesDao().ifExists(movie.getMovie_id());
            checkResult = checkMovieId > 0;
            return checkResult;
        }


        @Override
        protected void onPostExecute(Boolean checkResult) {
            checkMovieInFavorites = checkResult;
            if (checkMovieInFavorites) {
                fab.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

            } else {
                fab.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));

            }
        }
    }
}
