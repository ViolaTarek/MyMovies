package com.example.mymovies.MovieList.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.Helper.GlideApp;
import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.R;
import com.example.mymovies.MovieDetails.View.MovieDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagingAdapter extends PagedListAdapter<MovieModel, PagingAdapter.MovieViewHolder> {

    private Context context;
    Boolean isExist = false;
    Boolean isExist_wishlist = false;
    ProgressBar bar;

    public PagingAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.bar = bar;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.index_home_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        MovieModel movie = getItem(position);
        if (movie != null) {
            holder.title.setText(movie.getMovie_original_Title());
            holder.rate.setText(Double.toString(movie.getVoteAverage()));
            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
            GlideApp.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("id",movie.getMovie_id());
                context.startActivity(intent);
            });
        }


    }

    private static DiffUtil.ItemCallback<MovieModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieModel oldItem, @NonNull MovieModel newItem) {
                    return oldItem.getMovie_id() == newItem.getMovie_id();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieModel oldItem, @NonNull MovieModel newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.rate)
        TextView rate;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
