package com.example.mymovies.MovieList.DataSource;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;


import com.example.mymovies.BuildConfig;
import com.example.mymovies.MovieList.Listeners.NoMovieFoundCallback;
import com.example.mymovies.Models.GetMoviesResponse;
import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.Retrofit.WebClient;
import com.example.mymovies.Retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, MovieModel> {
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private String type;
    private int id;
    ProgressBar bar;
    String search;
    NoMovieFoundCallback noMovieFoundCallback;

    public MovieDataSource(String type, int id, String search, ProgressBar bar, NoMovieFoundCallback noMovieFoundCallback) {
        this.type = type;
        this.id = id;
        this.search = search;
        this.bar = bar;
        this.noMovieFoundCallback = noMovieFoundCallback;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieModel> callback) {
        WebService api = WebClient.getWebService();
        if (type.equals("popular")) {
            Call<GetMoviesResponse> call = api.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, FIRST_PAGE);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                    if (response.body() != null) {
                        bar.setVisibility(View.GONE);

                        callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        } else if (type.equals("top_rated")) {
            Call<GetMoviesResponse> call = api.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, FIRST_PAGE);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                    if (response.body() != null) {
                        bar.setVisibility(View.GONE);

                        callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });

        } else if (type.equals("search")) {
            Call<GetMoviesResponse> call = api.searchMovie(BuildConfig.THE_MOVIE_DB_API_TOKEN, search, FIRST_PAGE);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                    if (response.body() != null) {
                        bar.setVisibility(View.GONE);
                        if (response.body().getResults().size() != 0) {
                            callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                        } else {
                            noMovieFoundCallback.searchEmpty();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        }


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieModel> callback) {
        WebService api = WebClient.getWebService();
        if (type.equals("popular")) {

            Call<GetMoviesResponse> call = api.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, params.key);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    if (response.body() != null) {
                        callback.onResult(response.body().getResults(), key);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        } else if (type.equals("top_rated")) {
            Call<GetMoviesResponse> call = api.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, params.key);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    if (response.body() != null) {
                        callback.onResult(response.body().getResults(), key);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        } else if (type.equals("search")) {
            Call<GetMoviesResponse> call = api.searchMovie(BuildConfig.THE_MOVIE_DB_API_TOKEN, search, params.key);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    if (response.body() != null) {
                        callback.onResult(response.body().getResults(), key);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieModel> callback) {
        WebService api = WebClient.getWebService();
        if (type.equals("popular")) {

            Call<GetMoviesResponse> call = api.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, params.key);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {

                    if (response.body() != null) {
                        Integer key;
                        if (response.body().getResults().size() < 10) {
                            key = null;
                        } else key = params.key + 1;

                        callback.onResult(response.body().getResults(), key);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        } else if (type.equals("top_rated")) {
            Call<GetMoviesResponse> call = api.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, params.key);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {

                    if (response.body() != null) {
                        Integer key;
                        if (response.body().getResults().size() < 10) {
                            key = null;
                        } else key = params.key + 1;

                        callback.onResult(response.body().getResults(), key);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        } else if (type.equals("search")) {

            Call<GetMoviesResponse> call = api.searchMovie(BuildConfig.THE_MOVIE_DB_API_TOKEN, search, params.key);
            call.enqueue(new Callback<GetMoviesResponse>() {
                @Override
                public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {

                    if (response.body() != null) {
                        Integer key;
                        if (response.body().getResults().size() < 10) {
                            key = null;
                        } else key = params.key + 1;

                        callback.onResult(response.body().getResults(), key);
                    }
                }

                @Override
                public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

                }
            });
        }
    }
}
