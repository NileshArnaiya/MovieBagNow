package com.greedygame.moviebagnow.activities.review.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.moviebagnow.R
import com.greedygame.moviebagnow.activities.detail.viewModel.ViewModelFactory
import com.greedygame.moviebagnow.activities.review.viewModel.ReviewViewModel
import com.greedygame.moviebagnow.adapters.ReviewAdapter
import com.greedygame.moviebagnow.models.Resource
import com.greedygame.moviebagnow.models.ReviewModel
import com.greedygame.moviebagnow.models.ReviewResponse
import com.greedygame.moviebagnow.utility.Constants
import java.util.*

class ReviewScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        init()
    }

    private fun init() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val reviews = ArrayList<ReviewModel>()
        val reviewAdapter = ReviewAdapter(this, reviews)
        val rv_review = findViewById<RecyclerView>(R.id.rv_review)
        rv_review.layoutManager = LinearLayoutManager(this)
        rv_review.adapter = reviewAdapter
        val mViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                intent.getStringExtra(Constants.MOVIE_ID)!!,
                ViewModelFactory.Type.REVIEW
            )
        ).get(ReviewViewModel::class.java)
        mViewModel.review.observe(this, { reviewResource: Resource<ReviewResponse> ->
            findViewById<View>(R.id.progressBar).visibility = View.GONE
            if (reviewResource.data!!.results!!.isEmpty()) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            }
            when (reviewResource.status) {
                Resource.Status.SUCCESS -> if (reviewResource.data.results != null) {
                    reviews.addAll(reviewResource.data.results!!)
                    reviewAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> if (reviewResource.data.results != null) {
                    Toast.makeText(this, reviewResource.data.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}