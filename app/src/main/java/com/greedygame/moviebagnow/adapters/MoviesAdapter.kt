package com.greedygame.moviebagnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.greedygame.moviebagnow.R
import com.greedygame.moviebagnow.models.MovieModel
import com.greedygame.moviebagnow.utility.Constants
import java.util.*

private class MoviesAdapter(
    private val mContext: Context,
    private val mMovies: ArrayList<MovieModel>,
    var mClickListenerPopularMovie: ClickListenerPopularMovie
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    var mRequestOptions: RequestOptions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_title.text = mMovies[position].title
        holder.tv_vote_average.text =
            mContext.getString(R.string.vote) + mMovies[position].vote_average
        holder.tv_language.text =
            mContext.getString(R.string.language) + mMovies[position].original_language
        holder.tv_release_date.text =
            mContext.getString(R.string.release_date) + mMovies[position].release_date
        Glide.with(mContext)
            .load(Constants.IMAGE_BASE_URL + mMovies[position].poster_path)
            .apply(mRequestOptions)
            .into(holder.iv_popular_movie)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tv_title: TextView
        var tv_vote_average: TextView
        var tv_language: TextView
        var tv_release_date: TextView
        var iv_popular_movie: ImageView
        override fun onClick(v: View) {
            mClickListenerPopularMovie.onClickPopularMovie(adapterPosition)
        }

        init {
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_vote_average = itemView.findViewById(R.id.tv_vote_average)
            tv_language = itemView.findViewById(R.id.tv_language)
            tv_release_date = itemView.findViewById(R.id.tv_release_date)
            iv_popular_movie = itemView.findViewById(R.id.iv_popular_movie)
            itemView.setOnClickListener(this)
        }
    }

    interface ClickListenerPopularMovie {
        fun onClickPopularMovie(position: Int)
    }

    init {
        mRequestOptions = RequestOptions()
        mRequestOptions = mRequestOptions.transforms(CenterCrop(), RoundedCorners(16))
    }
}