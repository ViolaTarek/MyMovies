package com.example.mymovies.Favourites;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mymovies.Models.MovieModel;

import java.util.List;

public class FavoriteRep {

    Favorite favoriteDao;
    private LiveData<List<MovieModel>> allFavorites;

    public FavoriteRep(Application application) {
        FavoriteDb database = FavoriteDb.getInstance(application);
        favoriteDao = database.favoritesDao();
        allFavorites = favoriteDao.getAllFavorites();

    }

    public LiveData<List<MovieModel>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(MovieModel fav) {
        new InsertFavoritesAsyncTask(favoriteDao).execute(fav);

    }
    private static class InsertFavoritesAsyncTask extends AsyncTask<MovieModel, Void, Void> {
        private Favorite AsyncDao;


        private InsertFavoritesAsyncTask(Favorite Fav) {
           this.AsyncDao = Fav;
        }

        @Override
        protected Void doInBackground(final MovieModel... movieResults) {
            AsyncDao.insert(movieResults[0]);
            return null;
        }
    }


    public void deleteAllFavorites() {
        new DeleteAllFavoritesAsyncTask(favoriteDao).execute();
    }

    private static class DeleteAllFavoritesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Favorite mAsyncDao;

        private DeleteAllFavoritesAsyncTask(Favorite favorite) {
            mAsyncDao = favorite;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            mAsyncDao.deleteAllFavorites();
            return null;
        }
    }


}
