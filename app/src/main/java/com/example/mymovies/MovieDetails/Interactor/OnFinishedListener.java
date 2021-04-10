package com.example.mymovies.MovieDetails.Interactor;

import com.example.mymovies.Models.MovieModel;

public interface OnFinishedListener {
    void OnSuccess(MovieModel movieModel);
    void OnFailed(String message);
}
