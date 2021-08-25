package com.greedygame.moviebagnow.adapters

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.viewpager.widget.ViewPager
import com.greedygame.moviebagnow.R
import com.greedygame.moviebagnow.models.MovieModel
import com.greedygame.moviebagnow.utility.Constants
import java.util.ArrayList

class RestMovieAdapter(
    private val mContext: Context,
    private val mMovies: ArrayList<MovieModel>,
    private val mClickListenerNowPlayingMovie: ClickListenerNowPlayingMovie
) : PagerAdapter() {
    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val itemView = inflater.inflate(R.layout.item_playing, null)
        val iv_now_playing_movie = itemView.findViewById<ImageView>(R.id.iv_now_playing_movie)
        val tv_now_playing_movie_name =
            itemView.findViewById<TextView>(R.id.tv_now_playing_movie_name)
        val tv_now_playing_movie_release_date =
            itemView.findViewById<TextView>(R.id.tv_now_playing_release_date)
        tv_now_playing_movie_name.text = mMovies[position].title
        tv_now_playing_movie_release_date.text = mMovies[position].release_date
        Glide.with(mContext)
            .load(Constants.IMAGE_BASE_URL + mMovies[position].poster_path)
            .into(iv_now_playing_movie)
        itemView.setOnClickListener { v: View? ->
            mClickListenerNowPlayingMovie.onClickNowPlayingMovie(
                position
            )
        }
        collection.addView(itemView)
        return itemView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return Math.min(mMovies.size, 5)
    }

    override fun destroyItem(container: View, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    interface ClickListenerNowPlayingMovie {
        fun onClickNowPlayingMovie(position: Int)
    }
}