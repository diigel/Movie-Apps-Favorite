package com.dhani.movieapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhani.movieapps.R
import com.dhani.movieapps.adapter.holder.MovieViewHolder
import com.dhani.movieapps.adapter.holder.NetworkStateViewHolder
import com.dhani.movieapps.network.response.DataResultsMovie
import com.dhani.movieapps.utils.DiffUtilsMovie
import com.dhani.movieapps.utils.NetworkState

class MovieAdapter(private var onItemClickCallback: OnItemClickCallback? = null) :
    PagedListAdapter<DataResultsMovie, RecyclerView.ViewHolder>(DiffUtilsMovie()) {

    private val movieAdapterType = 1
    private val networkViewType = 2
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == movieAdapterType) {
            view = LayoutInflater.from(container.context).inflate(R.layout.item_list, container, false)
            MovieViewHolder(view = view,onItemClickCallback = onItemClickCallback!!)
        } else {
            view = LayoutInflater.from(container.context).inflate(R.layout.no_item_layout, container, false)
            NetworkStateViewHolder(view = view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            networkViewType
        else
            movieAdapterType
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == movieAdapterType){
            val item = getItem(position)
            if (item!=null){
                (holder as MovieViewHolder).bind(item)
            }
        } else
            (holder as NetworkStateViewHolder).bind(networkState!!)
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(dataMovie: DataResultsMovie)
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}