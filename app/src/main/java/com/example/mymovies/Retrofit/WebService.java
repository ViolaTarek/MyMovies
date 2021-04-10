package com.example.mymovies.Retrofit;

import com.example.mymovies.Models.GetMoviesResponse;
import com.example.mymovies.Models.MovieModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.mymovies.Helper.URL.GET_MOVIE;
import static com.example.mymovies.Helper.URL.POPULAR_MOVIES;
import static com.example.mymovies.Helper.URL.SEARCH_MOVIES;
import static com.example.mymovies.Helper.URL.TOP_RATED_MOVIES;

public interface WebService {
    @GET(POPULAR_MOVIES)
    Call<GetMoviesResponse> getPopularMovies(@Query("api_key") String apiKey,@Query("page") int page);

    @GET(TOP_RATED_MOVIES)
    Call<GetMoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey,@Query("page") int page);

    @GET(SEARCH_MOVIES)
    Call<GetMoviesResponse> searchMovie(@Query("api_key") String apiKey,@Query("query") String search,@Query("page") int page);

    @GET(GET_MOVIE)
    Call<MovieModel> getMovie(@Path("movie_id")Integer movieId,@Query("api_key") String apiKey);

}
