package com.example.mymovies.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
@Entity(tableName = "favorites_table")

public class MovieModel implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private int movie_id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster_path;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String movie_original_Title;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private Integer voteCount;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @Ignore
    @SerializedName("genres")
    private ArrayList<Genre> genres;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String date_of_release;

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate_of_release() {
        return date_of_release;
    }

    public void setDate_of_release(String date_of_release) {
        this.date_of_release = date_of_release;
    }

    public String getMovie_original_Title() {
        return movie_original_Title;
    }

    public void setMovie_original_Title(String movie_original_Title) {
        this.movie_original_Title = movie_original_Title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
