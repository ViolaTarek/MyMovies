package com.example.mymovies.Favourites;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymovies.Models.MovieModel;

@Database(entities = {MovieModel.class}, version = 2, exportSchema = false)

public abstract class FavoriteDb extends RoomDatabase {

    private static FavoriteDb instance;

    public static FavoriteDb getInstance(Context context) {

        if (instance == null) {
            synchronized (FavoriteDb.class) {

                instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDb.class, "favorites_database.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }}
        return instance;
    }

    public abstract Favorite favoritesDao();
}


