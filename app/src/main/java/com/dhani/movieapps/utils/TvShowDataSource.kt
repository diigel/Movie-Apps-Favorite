package com.dhani.movieapps.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.dhani.movieapps.network.Network
import com.dhani.movieapps.network.response.DataResultTv
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowDataSource(private val composite: CompositeDisposable) : ItemKeyedDataSource<Int, DataResultTv>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private var page = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<DataResultTv>
    ) {
        networkState.postValue(NetworkState.LOADING)
        val compositeDisposable = Network.getRoutesRx().getTvShow(page = page)
            .subscribeOn(Schedulers.io())
            .doOnNext { page = it.page + 1 }
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    callback.onResult(it)
                    networkState.postValue(NetworkState.LOADED)
                }
            }, {
                networkState.postValue(NetworkState.ERROR)
                it.message?.let {
                    Log.e("Error Tv Init ", it)
                }
            })

        composite.add(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<DataResultTv>) {
        networkState.postValue(NetworkState.LOADING)
        val disposable = Network.getRoutesRx().getTvShow(page = page)
            .subscribeOn(Schedulers.io())
            .filter { it.results != null }
            .doOnNext { page = it.page + 1 }
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    callback.onResult(it)
                    networkState.postValue(NetworkState.LOADED)
                }else{
                    networkState.postValue(NetworkState.END_OF_PAGE)
                }
            }, {
                networkState.postValue(NetworkState.ERROR)
                it.message?.let {
                    Log.e("Error After Tv",it)
                }
            })

        composite.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<DataResultTv>) {
    }

    override fun getKey(item: DataResultTv): Int = item.id
}