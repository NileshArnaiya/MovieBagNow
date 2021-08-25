package com.greedygame.moviebagnow.activities.detail.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greedygame.moviebagnow.central.ReturnMovieDetails
import com.greedygame.moviebagnow.models.MovieModel
import com.greedygame.moviebagnow.models.MovieResponse
import com.greedygame.moviebagnow.models.Resource

class DetailsViewModel(movie_id: String?) : ViewModel() {
    val movieDetails: MutableLiveData<Resource<MovieModel>>
    val similarMovie: MutableLiveData<Resource<MovieResponse>>

    init {
        val movieListRepository = ReturnMovieDetails()
        movieDetails = movieListRepository.getMovieDetails(movie_id)
        similarMovie = movieListRepository.getSimilarMovieResult(movie_id)
    }
}