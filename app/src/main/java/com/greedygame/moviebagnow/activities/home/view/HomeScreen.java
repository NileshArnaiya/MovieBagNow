package com.greedygame.moviebagnow.activities.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.greedygame.moviebagnow.R;
import com.greedygame.moviebagnow.activities.detail.view.DetailScreen;
import com.greedygame.moviebagnow.activities.home.viewModel.MoviesViewModel;
import com.greedygame.moviebagnow.adapters.RestMovieAdapter;
import com.greedygame.moviebagnow.adapters.PopularMoviesAdapter;
import com.greedygame.moviebagnow.models.MovieModel;
import com.greedygame.moviebagnow.utility.Constants;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity implements PopularMoviesAdapter.ClickListenerPopularMovie, RestMovieAdapter.ClickListenerNowPlayingMovie {

    public ArrayList<MovieModel> mNowPlayingMovie, mPopularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        MoviesViewModel mViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        mNowPlayingMovie = new ArrayList<>();
        RestMovieAdapter restMovieAdapter = new RestMovieAdapter(this, mNowPlayingMovie,this);
        ViewPager vp_now_playing_movies = findViewById(R.id.viewpager_movies);
        vp_now_playing_movies.setAdapter(restMovieAdapter);
        TabLayout tb_now_playing_movies = findViewById(R.id.movies_tab);
        tb_now_playing_movies.setupWithViewPager(vp_now_playing_movies);

        mPopularMovies = new ArrayList<>();
        PopularMoviesAdapter popularMovieAdapter = new PopularMoviesAdapter(this, mPopularMovies, this);
        RecyclerView rv_popular_movies = findViewById(R.id.rv_popular_movies);
        rv_popular_movies.setLayoutManager(new LinearLayoutManager(this));
        rv_popular_movies.setAdapter(popularMovieAdapter);

        mViewModel.getNowplayingMovie().observe(this, nowPlayingMovieResource -> {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            switch (nowPlayingMovieResource.getStatus())
            {
                case SUCCESS:
                    if (nowPlayingMovieResource.getData() != null) {
                        mNowPlayingMovie.addAll(nowPlayingMovieResource.getData().getResults());
                        restMovieAdapter.notifyDataSetChanged();
                        vp_now_playing_movies.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (vp_now_playing_movies.getCurrentItem() + 1 < Math.min(mNowPlayingMovie.size(), 5)) {
                                    vp_now_playing_movies.setCurrentItem(vp_now_playing_movies.getCurrentItem() + 1, true);
                                } else {
                                    vp_now_playing_movies.setCurrentItem(0, true);
                                }
                                vp_now_playing_movies.postDelayed(this, Constants.VIEW_TIME);
                            }
                        }, Constants.VIEW_TIME);
                    }
                    break;

                case ERROR:
                    if (nowPlayingMovieResource.getData() != null) {
                        Toast.makeText(this, nowPlayingMovieResource.getData().toString(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });

        mViewModel.getPopularMovieResult().observe(this, popularMoviewResultResource -> {
            switch (popularMoviewResultResource.getStatus())
            {
                case SUCCESS:
                    if (popularMoviewResultResource.getData() != null) {
                        mPopularMovies.addAll(popularMoviewResultResource.getData().getResults());
                        popularMovieAdapter.notifyDataSetChanged();
                    }
                    break;

                case ERROR:
                    if (popularMoviewResultResource.getData() != null) {
                        Toast.makeText(this, popularMoviewResultResource.getData().toString(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });


    }

    @Override
    public void onClickPopularMovie(int position) {
        openMovieDetailsActivity(mPopularMovies.get(position).getId());
    }

    @Override
    public void onClickNowPlayingMovie(int position) {
        openMovieDetailsActivity(mNowPlayingMovie.get(position).getId());
    }

    private void openMovieDetailsActivity(String movieId) {
        Intent intent = new Intent(this, DetailScreen.class);
        intent.putExtra(Constants.MOVIE_ID, movieId);
        startActivity(intent);
    }
}