package com.greedygame.moviebagnow.activities.cast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greedygame.moviebagnow.central.ReturnCast
import com.greedygame.moviebagnow.models.CastResponse
import com.greedygame.moviebagnow.models.Resource

class CastViewModel(movie_id: String?) : ViewModel() {
    val cast: MutableLiveData<Resource<CastResponse>>

    init {
        val returnCast = ReturnCast()
        cast = returnCast.getCast(movie_id)
    }
}