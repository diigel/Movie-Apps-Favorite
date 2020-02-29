package com.dhani.movieapps.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhani.movieapps.R
import com.dhani.movieapps.utils.NetworkState
import kotlinx.android.synthetic.main.no_item_layout.view.*

class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(networkState: NetworkState?) = itemView.run {
        when (networkState) {
            NetworkState.LOADED -> {
                progress.visibility = View.VISIBLE
                img_no_item.visibility = View.GONE
                text_state.visibility = View.GONE
            }
            NetworkState.LOADING -> {
                progress.visibility = View.VISIBLE
                img_no_item.visibility = View.GONE
                text_state.visibility = View.GONE
            }
            NetworkState.END_OF_PAGE -> {
                progress.visibility = View.GONE
                img_no_item.visibility = View.GONE
                text_state.visibility = View.VISIBLE
                text_state.text = networkState.message
            }
            NetworkState.ERROR -> {
                progress.visibility = View.GONE
                img_no_item.visibility = View.VISIBLE
                text_state.visibility = View.VISIBLE
                Glide.with(this).asDrawable().load(R.drawable.error).into(img_no_item)
                text_state.text = networkState.message
            }
        }
    }

}