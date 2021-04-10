package com.example.mymovies.MovieList.viewmodel;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.mymovies.MovieList.DataSource.MovieDataSource;
import com.example.mymovies.MovieList.DataSource.MovieDataSourceFactory;
import com.example.mymovies.MovieList.Listeners.NoMovieFoundCallback;
import com.example.mymovies.Models.MovieModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesViewModel extends ViewModel {
    public LiveData<PagedList<MovieModel>> moviesPagedlist;
    public LiveData<PageKeyedDataSource<Integer, MovieModel>> liveDataDource;

    public MoviesViewModel(String type, int id, ProgressBar bar, NoMovieFoundCallback emptyCallback, String search) {

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE)
                        .build();

        MovieDataSourceFactory factory = new MovieDataSourceFactory(type, id, bar, search, emptyCallback);
        Executor executor = Executors.newFixedThreadPool(5);
        liveDataDource = factory.getMovieLiveDataSource();

        moviesPagedlist = (new LivePagedListBuilder(factory, config))
                .setFetchExecutor(executor)
                .build();
    }

}
