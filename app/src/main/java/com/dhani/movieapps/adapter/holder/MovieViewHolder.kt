package com.dhani.movieapps.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhani.movieapps.BuildConfig
import com.dhani.movieapps.adapter.MovieAdapter
import com.dhani.movieapps.network.response.DataResultsMovie
import kotlinx.android.synthetic.main.item_list.view.*

class MovieViewHolder(view: View,
                      private val onItemClickCallback
                      : MovieAdapter.OnItemClickCallback)
    : RecyclerView.ViewHolder(view) {

    fun bind(dataMovie: DataResultsMovie?) = itemView.run {
        Glide.with(this).load(BuildConfig.IMG_URL + dataMovie?.backdropPath).into(img_background_path)
        Glide.with(this).load(BuildConfig.IMG_URL + dataMovie?.posterPath).into(img_info)
        txt_title.text = dataMovie?.title
        txt_release.text = dataMovie?.releaseDate
        txt_description.text = dataMovie?.overview
        txt_rate.text = dataMovie?.voteAverage.toString()

        setOnClickListener { onItemClickCallback.onItemClicked(dataMovie!!) }
    }

}