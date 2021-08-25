package com.greedygame.moviebagnow.models

import java.util.*

class MovieModel {
    var id: String? = null
    var release_date: String? = null
    var title: String? = null
    var overview: String? = null
    var popularity: String? = null
    var poster_path: String? = null
    var vote_average: String? = null
    var original_language: String? = null
    var status: String? = null
    var production_companies: ArrayList<ProdHouses>? = null
}