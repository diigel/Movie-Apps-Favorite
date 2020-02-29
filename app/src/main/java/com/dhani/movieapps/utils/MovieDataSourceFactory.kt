package com.dhani.movieapps.utils

import androidx.lifecycle.MutableLiveData
import com.dhani.movieapps.network.response.DataResultsMovie
import io.reactivex.disposables.CompositeDisposable
import androidx.paging.DataSource

class MovieDataSourceFactory(private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int,DataResultsMovie>(){

    val dataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, DataResultsMovie> {
        val movieDataSourceFactory = MovieDataSource(compositeDisposable = compositeDisposable)
        dataSource.postValue(movieDataSourceFactory)
        return movieDataSourceFactory
    }
}