package com.example.mymovies.MovieList.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymovies.Favourites.FavoriteViewModel;
import com.example.mymovies.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.search_et)
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViewFragment();
        setupBottomNavigation();
    }

    private void setupViewFragment() {
        //show popular movies by default
        toolbarTitle.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);
        toolbarTitle.setText(getString(R.string.popular_bar));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MoviesListFragment.newInstance("popular")).commit();

    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.popular:
                    toolbarTitle.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    toolbarTitle.setText(getString(R.string.popular_bar));
                    CloseOpenedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MoviesListFragment.newInstance("popular")).commit();

                    return true;
                case R.id.topRated:
                    toolbarTitle.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    toolbarTitle.setText(getString(R.string.top_rated_bar));
                    CloseOpenedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MoviesListFragment.newInstance("top_rated")).commit();
                    return true;

                case R.id.search:
                    toolbarTitle.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                    CloseOpenedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MoviesListFragment.newInstance("search")).commit();
                    return true;
                case R.id.favorites:
                    toolbarTitle.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    toolbarTitle.setText(getString(R.string.favourite_movies));
                    CloseOpenedFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MoviesListFragment.newInstance("fav")).commit();
                    return true;
            }
            return false;
        });
    }

    private void CloseOpenedFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            getFragmentManager().popBackStackImmediate();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }
}
