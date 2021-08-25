package com.greedygame.moviebagnow.activities.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greedygame.moviebagnow.activities.cast.viewModel.CastViewModel
import com.greedygame.moviebagnow.activities.review.viewModel.ReviewViewModel

class ViewModelFactory(private val mMovieId: String?, private val mType: Type) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (mType) {
            Type.MOVIE_DETAILS -> DetailsViewModel(mMovieId) as T
            Type.CAST -> CastViewModel(mMovieId) as T
            Type.REVIEW -> ReviewViewModel(mMovieId) as T
        }
    }

    enum class Type {
        MOVIE_DETAILS, CAST, REVIEW
    }
}