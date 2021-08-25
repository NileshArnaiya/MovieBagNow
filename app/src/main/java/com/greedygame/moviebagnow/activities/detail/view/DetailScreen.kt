package com.greedygame.moviebagnow.activities.detail.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.greedygame.moviebagnow.R
import com.greedygame.moviebagnow.activities.cast.view.CastScreen
import com.greedygame.moviebagnow.activities.detail.viewModel.DetailsViewModel
import com.greedygame.moviebagnow.activities.detail.viewModel.ViewModelFactory
import com.greedygame.moviebagnow.activities.review.view.ReviewScreen
import com.greedygame.moviebagnow.adapters.SimilarMoviesAdapter
import com.greedygame.moviebagnow.adapters.SimilarMoviesAdapter.ClickListener
import com.greedygame.moviebagnow.models.MovieModel
import com.greedygame.moviebagnow.models.MovieResponse
import com.greedygame.moviebagnow.models.Resource
import com.greedygame.moviebagnow.models.SimilarMoviesModel
import com.greedygame.moviebagnow.utility.Constants
import java.util.*

class DetailScreen : AppCompatActivity(), ClickListener {
    private var mSimilarMovie: ArrayList<SimilarMoviesModel>? = null
    private var mMoviesId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()
    }

    private fun initView() {
        val iv_movie = findViewById<ImageView>(R.id.iv_movie)
        val tv_title = findViewById<TextView>(R.id.tv_title)
        val tv_vote_average = findViewById<TextView>(R.id.tv_vote_average)
        val tv_language = findViewById<TextView>(R.id.tv_language)
        val tv_popularity = findViewById<TextView>(R.id.tv_popularity)
        val tv_release_date = findViewById<TextView>(R.id.tv_release_date)
        val tv_release_status = findViewById<TextView>(R.id.tv_release_status)
        val tv_overview = findViewById<TextView>(R.id.tv_overview)
        findViewById<View>(R.id.tv_casts).setOnClickListener { v: View? -> openActivity(true) }
        findViewById<View>(R.id.tv_reviews).setOnClickListener { v: View? -> openActivity(false) }
        findViewById<View>(R.id.iv_back_button).setOnClickListener { v: View? -> finish() }
        val rv_production_companies = findViewById<RecyclerView>(R.id.rv_production_companies)
        val productions = ArrayList<SimilarMoviesModel>()
        val productionsAdapter = SimilarMoviesAdapter(this, productions, null)
        rv_production_companies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_production_companies.adapter = productionsAdapter
        mSimilarMovie = ArrayList()
        val similarMovieAdapter = SimilarMoviesAdapter(this, mSimilarMovie, this)
        val rv_similar_movies = findViewById<RecyclerView>(R.id.rv_similar_movies)
        rv_similar_movies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_similar_movies.adapter = similarMovieAdapter
        val mViewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                intent.getStringExtra(
                    Constants.MOVIE_ID
                ), ViewModelFactory.Type.MOVIE_DETAILS
            )
        ).get(
            DetailsViewModel::class.java
        )
        mViewModel.movieDetails.observe(this, { movieResource: Resource<MovieModel>? ->
            findViewById<View>(R.id.progressBar).visibility = View.GONE
            if (movieResource != null) {
                when (movieResource.status) {
                    Resource.Status.SUCCESS -> if (movieResource.data != null) {
                        val movie = movieResource.data
                        mMoviesId = movie.id
                        tv_title.text = movie.title
                        tv_vote_average.text = getString(R.string.vote) + movie.vote_average
                        tv_language.text = getString(R.string.language) + movie.original_language
                        tv_popularity.text = getString(R.string.popularity) + movie.popularity
                        tv_release_date.text = getString(R.string.release_date) + movie.release_date
                        tv_release_status.text = getString(R.string.release_status) + movie.status
                        tv_overview.text = movie.overview
                        var requestOptions = RequestOptions()
                        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
                        Glide.with(this)
                            .load(Constants.IMAGE_BASE_URL + movie.poster_path)
                            .apply(requestOptions)
                            .into(iv_movie)
                        for (productionCompanies in movie.production_companies!!) {
                            val similarMoviesModel = productionCompanies.id?.let {
                                productionCompanies.name?.let { it1 ->
                                    productionCompanies.logo_path?.let { it2 ->
                                        SimilarMoviesModel(
                                            it,
                                            it1,
                                            it2
                                        )
                                    }
                                }
                            }
                            similarMoviesModel?.let { productions.add(it) }
                        }
                        productionsAdapter.notifyDataSetChanged()
                    }
                    Resource.Status.ERROR -> if (movieResource.data != null) {
                        Toast.makeText(this, movieResource.data.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        mViewModel.similarMovie.observe(this, { similarMovieResource: Resource<MovieResponse>? ->
            if (similarMovieResource != null) {
                when (similarMovieResource.status) {
                    Resource.Status.SUCCESS -> if (similarMovieResource.data != null) {
                        for (movie in similarMovieResource.data.results!!) {
                            val similarMoviesModel =
                                SimilarMoviesModel(movie.id!!, movie.title!!, movie.poster_path!!)
                            mSimilarMovie!!.add(similarMoviesModel)
                        }
                        similarMovieAdapter.notifyDataSetChanged()
                    }
                    Resource.Status.ERROR -> if (similarMovieResource.data != null) {
                        Toast.makeText(this, similarMovieResource.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun openActivity(isCast: Boolean) {
        val intent: Intent
        intent = if (isCast) {
            Intent(this, CastScreen::class.java)
        } else {
            Intent(this, ReviewScreen::class.java)
        }
        intent.putExtra(Constants.MOVIE_ID, mMoviesId)
        startActivity(intent)
    }

    override fun onClickSimilarMovie(position: Int) {
        val intent = Intent(this, DetailScreen::class.java)
        intent.putExtra(Constants.MOVIE_ID, mSimilarMovie!![position].id)
        startActivity(intent)
    }
}