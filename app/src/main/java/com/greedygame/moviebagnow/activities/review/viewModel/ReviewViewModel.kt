package com.greedygame.moviebagnow.activities.review.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greedygame.moviebagnow.central.ReturnReviews
import com.greedygame.moviebagnow.models.Resource
import com.greedygame.moviebagnow.models.ReviewResponse

class ReviewViewModel(movie_id: String?) : ViewModel() {
    val review: MutableLiveData<Resource<ReviewResponse>>

    init {
        val returnReviews = ReturnReviews()
        review = returnReviews.getReview(movie_id)
    }
}