package com.dhani.movieapps.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhani.movieapps.BuildConfig
import com.dhani.movieapps.adapter.TvShowAdapter
import com.dhani.movieapps.network.response.DataResultTv
import kotlinx.android.synthetic.main.item_list.view.*

class TvShowHolder(view: View,
                   private val onItemClickCallback
                   : TvShowAdapter.OnItemClickCallback)
    : RecyclerView.ViewHolder(view) {

    fun bindTvShow(dataTv : DataResultTv?) = itemView.run {
        Glide.with(this).load(BuildConfig.IMG_URL + dataTv?.backdropPath).into(img_background_path)
        Glide.with(this).load(BuildConfig.IMG_URL + dataTv?.posterPath).into(img_info)
        txt_title.text = dataTv?.originalName
        txt_release.text = dataTv?.firstAirDate
        txt_description.text = dataTv?.overview
        txt_rate.text = dataTv?.voteAverage.toString()
        setOnClickListener { onItemClickCallback.onItemClicked(dataTv!!) }
    }
}