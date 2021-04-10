package com.example.mymovies.MovieDetails.Interactor;

import com.example.mymovies.BuildConfig;
import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.Retrofit.WebClient;
import com.example.mymovies.Retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieInteractor implements IMovieInteractor {
    @Override
    public void getMovieResponse(OnFinishedListener listener, int movieId) {
        WebService mApiClientInterface = WebClient.getWebService();
        mApiClientInterface.getMovie(movieId, BuildConfig.THE_MOVIE_DB_API_TOKEN).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.body() != null) {
                    listener.OnSuccess(response.body());
                } else
                    listener.OnFailed("Server Error");
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                listener.OnFailed("Something Went wrong!");

            }
        });
    }
}
