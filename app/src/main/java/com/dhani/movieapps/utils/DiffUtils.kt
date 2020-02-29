package com.dhani.movieapps.utils

import androidx.recyclerview.widget.DiffUtil
import com.dhani.movieapps.network.response.DataResultTv
import com.dhani.movieapps.network.response.DataResultsMovie

class DiffUtilsMovie : DiffUtil.ItemCallback<DataResultsMovie>() {
    override fun areItemsTheSame(oldItem: DataResultsMovie, newItem: DataResultsMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataResultsMovie, newItem: DataResultsMovie): Boolean {
        return oldItem == newItem
    }
}

class DiffUtilsTvShow : DiffUtil.ItemCallback<DataResultTv>() {
    override fun areItemsTheSame(oldItem: DataResultTv, newItem: DataResultTv): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: DataResultTv, newItem: DataResultTv): Boolean =
        oldItem == newItem

}