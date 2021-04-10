package com.example.mymovies.MovieList.DataSource;


import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.mymovies.MovieList.Listeners.NoMovieFoundCallback;
import com.example.mymovies.Models.MovieModel;

public class MovieDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, MovieModel>> movieLiveDataSource = new MutableLiveData<>();
    private String type;
    private int id;
    private String search;
    ProgressBar bar;
    NoMovieFoundCallback emptyCallback;

    public MovieDataSourceFactory(String type, int id, ProgressBar bar, String search, NoMovieFoundCallback emptyCallback) {
        this.type = type;
        this.id = id;
        this.search = search;
        this.bar = bar;
        this.emptyCallback = emptyCallback;
    }

    @Override
    public DataSource create() {
        MovieDataSource dataSource = new MovieDataSource(type, id, search, bar, emptyCallback);
        movieLiveDataSource.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, MovieModel>> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }
}
