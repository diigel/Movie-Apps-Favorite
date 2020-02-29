package com.dhani.movieapps.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.dhani.movieapps.network.Network
import com.dhani.movieapps.network.response.DataResultsMovie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val compositeDisposable: CompositeDisposable):ItemKeyedDataSource<Int,DataResultsMovie>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private var page = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<DataResultsMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        val disposable = Network.getRoutesRx().getMovie(page = page)
            .subscribeOn(Schedulers.io())
            .doOnNext { page = it.page + 1 }
            .map { it.results!! }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null){
                    callback.onResult(it)
                    networkState.postValue(NetworkState.LOADED)
                }
            },{
                networkState.postValue(NetworkState.ERROR)
                it.message?.let {message ->
                    Log.e("Error Response Init",message)
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<DataResultsMovie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            Network.getRoutesRx().getMovie(page = page)
                .subscribeOn(Schedulers.io())
                .filter { it.results != null }
                .doOnNext { page = it.page +1 }
                .subscribe({
                    Log.i("paramkey","paramkey->${params.key} -- paramkey +1 -> ${params.key+1}")
//                    if (it.totalPages >= page){
//                        callback.onResult(it.results!!)
//                        networkState.postValue(NetworkState.LOADED)
//                    }else{
//                        networkState.postValue(NetworkState.END_OF_PAGE)
//                    }
                    if (it != null){
                        callback.onResult(it.results!!)
                        networkState.postValue(NetworkState.LOADED)
                    }else{
                        networkState.postValue(NetworkState.END_OF_PAGE)
                    }
                },{
                    networkState.postValue(NetworkState.ERROR)
                    it.message?.let { message ->
                        Log.e("Error Data Source", message)
                    }

                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<DataResultsMovie>) {
    }

    override fun getKey(item: DataResultsMovie): Int = item.id
}