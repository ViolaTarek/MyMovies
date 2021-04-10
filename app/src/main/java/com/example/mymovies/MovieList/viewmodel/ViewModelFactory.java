package com.example.mymovies.MovieList.viewmodel;

import android.widget.ProgressBar;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymovies.MovieList.Listeners.NoMovieFoundCallback;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private String mParam;
    private int mid;
    private ProgressBar bar;
    private NoMovieFoundCallback emptyCallback;
    private String mSearch;

    public ViewModelFactory(String param, int id, ProgressBar bar, NoMovieFoundCallback emptyCallback, String Search) {
        mSearch = Search;
        mParam = param;
        mid = id;
        this.bar = bar;
        this.emptyCallback = emptyCallback;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MoviesViewModel(mParam, mid, bar, emptyCallback, mSearch);
    }
}