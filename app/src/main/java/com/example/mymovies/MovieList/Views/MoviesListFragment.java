package com.example.mymovies.MovieList.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.Favourites.FavoriteViewModel;
import com.example.mymovies.Models.MovieModel;
import com.example.mymovies.MovieList.Listeners.NoMovieFoundCallback;
import com.example.mymovies.MovieList.adapter.MovieAdapter;
import com.example.mymovies.R;
import com.example.mymovies.MovieList.adapter.PagingAdapter;
import com.example.mymovies.MovieList.viewmodel.MoviesViewModel;
import com.example.mymovies.MovieList.viewmodel.ViewModelFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListFragment extends Fragment implements NoMovieFoundCallback {

    private MoviesViewModel viewModel;
    PagingAdapter adapter2;
    String type;
    @BindView(R.id.empty_movies)
    TextView emptyMovies;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar bar;
    EditText search;
    private FavoriteViewModel favoriteViewModel;
    MovieAdapter adapter;

    public static MoviesListFragment newInstance(String type) {

        MoviesListFragment myFragment = new MoviesListFragment();

        Bundle args = new Bundle();
        args.putString("type", type);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        search = getActivity().findViewById(R.id.search_et);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bar.setVisibility(View.VISIBLE);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        type = getArguments().getString("type", "");
        setRecyclerView();
        if (type.equals("popular"))
            setUpPopularList();
        else if (type.equals("top_rated"))
            setUpTopRated();
        else if (type.equals("search")) {
            search.setText("");
            bar.setVisibility(View.GONE);
            onSearch();
        } else if (type.equals("fav")) {
            getFavorite();
        }
    }

    private void setRecyclerView() {
        adapter2 = new PagingAdapter(getActivity());
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 2);
        // setup recyclerView
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setUpPopularList() {
        viewModel = ViewModelProviders.of(this, new ViewModelFactory("popular", -1, bar, this, "")).get(MoviesViewModel.class);
        viewModel.moviesPagedlist.observe(this, products ->
                adapter2.submitList(products));
    }

    private void setUpTopRated() {
        viewModel = ViewModelProviders.of(this, new ViewModelFactory("top_rated", -1, bar, this, "")).get(MoviesViewModel.class);
        viewModel.moviesPagedlist.observe(this, products ->
                adapter2.submitList(products));

    }

    private void setUpSearch(String searchText) {
        viewModel = ViewModelProviders.of(this, new ViewModelFactory("search", -1, bar, this, searchText)).get(MoviesViewModel.class);
        viewModel.moviesPagedlist.observe(this, products ->
                adapter2.submitList(products));

    }

    @Override
    public void searchEmpty() {
        emptyMovies.setVisibility(View.VISIBLE);
    }

    public void onSearch() {
        search.setOnEditorActionListener((v, actionId, event) -> {
            bar.setVisibility(View.VISIBLE);
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String searchText = search.getText().toString();
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }
                setUpSearch(searchText);
                return true;
            }
            return false;
        });
    }

    private void getFavorite() {
        favoriteViewModel.getAllFavorites().observe(this, movieResults -> {
            adapter = new MovieAdapter(getActivity(), movieResults);
            adapter.setFavs(movieResults);
            final GridLayoutManager layoutManager =
                    new GridLayoutManager(getActivity(), 2);
            // setup recyclerView
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            bar.setVisibility(View.GONE);
        });
    }
}


