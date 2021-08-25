package com.greedygame.moviebagnow.activities.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greedygame.moviebagnow.central.ReturnMovieList
import com.greedygame.moviebagnow.models.MovieResponse
import com.greedygame.moviebagnow.models.Resource

class MoviesViewModel : ViewModel() {
    val nowplayingMovie: MutableLiveData<Resource<MovieResponse>>
    val popularMovieResult: MutableLiveData<Resource<MovieResponse>>

    init {
        val returnMovieList = ReturnMovieList()
        nowplayingMovie = returnMovieList.nowPlayingMovie
        popularMovieResult = returnMovieList.popularMovieResult
    }
}