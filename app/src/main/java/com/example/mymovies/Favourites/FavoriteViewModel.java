package com.example.mymovies.Favourites;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mymovies.Models.MovieModel;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRep FavRep;
    private LiveData<List<MovieModel>> allFavorites;

    public FavoriteViewModel( Application application) {
        super(application);
        FavRep = new FavoriteRep(application);
        allFavorites = FavRep.getAllFavorites();
    }
    public LiveData<List<MovieModel>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(MovieModel MovRes) {
        FavRep.insert(MovRes);
    }

    public void deleteAllFavorites() {
        FavRep.deleteAllFavorites();
    }

}
