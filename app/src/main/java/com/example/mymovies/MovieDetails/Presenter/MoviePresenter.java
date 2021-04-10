package com.example.mymovies.MovieDetails.Presenter;

import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.MovieDetails.Interactor.IMovieInteractor;
import com.example.mymovies.MovieDetails.Interactor.MovieInteractor;
import com.example.mymovies.MovieDetails.Interactor.OnFinishedListener;
import com.example.mymovies.MovieDetails.View.IMovieView;

public class MoviePresenter implements IMoviePresenter, OnFinishedListener {
    private IMovieInteractor interactor;
    private IMovieView view;

    public MoviePresenter( IMovieView view) {
        this.interactor = new MovieInteractor();
        this.view = view;
    }

    @Override
    public void OnSuccess(MovieModel movieModel) {
        view.onSuccess(movieModel);
    }

    @Override
    public void OnFailed(String message) {
        view.onFailed(message);
    }

    @Override
    public void getMovie(int id) {
        interactor.getMovieResponse(this, id);
    }
}
