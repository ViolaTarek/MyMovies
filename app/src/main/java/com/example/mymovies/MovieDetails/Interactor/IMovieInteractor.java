package com.example.mymovies.MovieDetails.Interactor;

import java.util.ArrayList;

public interface IMovieInteractor {
    void getMovieResponse(OnFinishedListener listener, int movieId);

}
