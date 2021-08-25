package com.greedygame.moviebagnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.greedygame.moviebagnow.R
import com.greedygame.moviebagnow.models.CastModel
import com.greedygame.moviebagnow.utility.Constants
import java.util.*

class CastAdapter(private val mContext: Context, private val mCasts: ArrayList<CastModel>) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    private var mRequestOptions: RequestOptions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = mCasts[position].name
        holder.tv_character.text = mCasts[position].character
        Glide.with(mContext)
            .load(Constants.IMAGE_BASE_URL + mCasts[position].profile_path)
            .centerCrop()
            .apply(mRequestOptions)
            .into(holder.iv_cast)
    }

    override fun getItemCount(): Int {
        return mCasts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_name: TextView
        var tv_character: TextView
        var iv_cast: ImageView

        init {
            iv_cast = itemView.findViewById(R.id.iv_cast)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_character = itemView.findViewById(R.id.tv_character)
        }
    }

    init {
        mRequestOptions = RequestOptions()
        mRequestOptions = mRequestOptions.transforms(CenterCrop(), RoundedCorners(16))
    }
}