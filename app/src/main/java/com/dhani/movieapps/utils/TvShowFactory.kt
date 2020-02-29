package com.dhani.movieapps.utils

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dhani.movieapps.network.response.DataResultTv
import io.reactivex.disposables.CompositeDisposable

class TvShowFactory (private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int,DataResultTv>(){

    val dataSource : MutableLiveData<TvShowDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, DataResultTv> {
        val factory = TvShowDataSource(composite = compositeDisposable)
        dataSource.postValue(factory)
        return factory
    }

}