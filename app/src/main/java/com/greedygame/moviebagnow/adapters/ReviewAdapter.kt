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
import com.greedygame.moviebagnow.models.ReviewModel
import com.greedygame.moviebagnow.utility.Constants
import java.util.*

class ReviewAdapter(private val mContext: Context, private val mReview: ArrayList<ReviewModel>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private var mRequestOptions: RequestOptions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_author_name.setText(mReview[position].author_details?.name)
        holder.tv_date.text = mReview[position].created_at!!.substring(0, 10)
        holder.tv_content.text = mReview[position].content
        Glide.with(mContext)
            .load(Constants.IMAGE_BASE_URL + mReview[position].author_details!!.avatar_path)
            .error(R.drawable.user)
            .apply(mRequestOptions)
            .into(holder.iv_author)
    }

    override fun getItemCount(): Int {
        return mReview.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_author_name: TextView
        var tv_date: TextView
        var tv_content: TextView
        var iv_author: ImageView

        init {
            iv_author = itemView.findViewById(R.id.iv_author)
            tv_author_name = itemView.findViewById(R.id.tv_author_name)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_content = itemView.findViewById(R.id.tv_content)
        }
    }

    init {
        mRequestOptions = RequestOptions()
        mRequestOptions = mRequestOptions.transforms(CenterCrop(), RoundedCorners(16))
    }
}