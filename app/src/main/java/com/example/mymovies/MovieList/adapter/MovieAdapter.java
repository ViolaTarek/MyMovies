package com.example.mymovies.MovieList.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.Helper.GlideApp;
import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.MovieDetails.View.MovieDetails;
import com.example.mymovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mcontext;
    private List<MovieModel> moviesList;

    public MovieAdapter(Context context, List<MovieModel> movies) {
        this.mcontext = context;
        this.moviesList = movies;
    }

    public void setFavs(List<MovieModel> results) {
        this.moviesList = results;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_home_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MyViewHolder holder, int position) {
        if (moviesList != null) {
            MovieModel current = moviesList.get(position);
            holder.title.setText(current.getMovie_original_Title());
            holder.rate.setText(Double.toString(current.getVoteAverage()));
            String imageUrl = "https://image.tmdb.org/t/p/w500" + current.getPoster_path();
            GlideApp.with(mcontext)
                    .load(imageUrl)
                    .into(holder.poster);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mcontext, MovieDetails.class);
                intent.putExtra("id", current.getMovie_id());
                mcontext.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView poster;
        public TextView rate;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            poster = itemView.findViewById(R.id.image);
            rate = itemView.findViewById(R.id.rate);


        }

    }

}
