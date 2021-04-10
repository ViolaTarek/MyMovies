package com.example.mymovies.MovieDetails.View;

import com.example.mymovies.Models.MovieModel;

public interface IMovieView {
    void onSuccess(MovieModel movieModel);

    void onFailed(String message);


}
