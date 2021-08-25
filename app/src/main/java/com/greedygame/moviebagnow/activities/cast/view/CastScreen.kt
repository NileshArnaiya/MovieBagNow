package com.greedygame.moviebagnow.activities.cast.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greedygame.moviebagnow.R
import com.greedygame.moviebagnow.activities.cast.viewModel.CastViewModel
import com.greedygame.moviebagnow.activities.detail.viewModel.ViewModelFactory
import com.greedygame.moviebagnow.adapters.CastAdapter
import com.greedygame.moviebagnow.models.CastModel
import com.greedygame.moviebagnow.models.CastResponse
import com.greedygame.moviebagnow.models.Resource
import com.greedygame.moviebagnow.utility.Constants
import java.util.*

class CastScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast)
        init()
    }

    private fun init() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        val casts = ArrayList<CastModel>()
        val castAdapter = CastAdapter(this, casts)
        val rv_cast = findViewById<RecyclerView>(R.id.rv_cast)
        rv_cast.layoutManager = GridLayoutManager(this, 2)
        rv_cast.adapter = castAdapter
        val mViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                intent.getStringExtra(Constants.MOVIE_ID)!!,
                ViewModelFactory.Type.CAST
            )
        ).get(CastViewModel::class.java)
        mViewModel.cast.observe(this, { castResource: Resource<CastResponse>? ->
            findViewById<View>(R.id.progressBar).visibility = View.GONE
            if (castResource != null) {
                when (castResource.status) {
                    Resource.Status.SUCCESS -> if (castResource.data != null) {
                        casts.addAll(Objects.requireNonNull(castResource.data.cast)!!)
                        castAdapter.notifyDataSetChanged()
                    }
                    Resource.Status.ERROR -> if (castResource.data != null) {
                        Toast.makeText(this, castResource.data.toString(), Toast.LENGTH_SHORT).show()
                    }
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