package com.example.mymovies.Favourites;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mymovies.Models.MovieModel;
import java.util.List;

@Dao
public interface Favorite {
    @Insert
    void insert(MovieModel results);

    @Query("DELETE FROM favorites_table")
    void deleteAllFavorites();

    @Query("SELECT * FROM favorites_table ORDER BY movie_id DESC")
    LiveData<List<MovieModel>> getAllFavorites();

    @Query("DELETE FROM favorites_table WHERE movie_id = :id")
    void deleteThisMovie(int id);

    @Query("SELECT COUNT(movie_id) FROM favorites_table WHERE movie_id = :id")
    Integer ifExists(int id);


}
