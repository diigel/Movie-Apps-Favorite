package com.dhani.movieapps.viewModel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhani.movieapps.network.Network
import com.dhani.movieapps.network.response.DetailTvShow
import com.dhani.movieapps.network.response.TvShow
import com.dhani.movieapps.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailTvShowViewModel : ViewModel() {

    private val networkState : MutableLiveData<NetworkState> = MutableLiveData()
    private val detailTvShowViewModel : MutableLiveData<DetailTvShow> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getTvShow(tvshowId : Int?): LiveData<DetailTvShow>{
        networkState.postValue(NetworkState.LOADING)
        val disposable = Network.getRoutesRx().getDetailTvShow(tvShowId = tvshowId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Handler().postDelayed({
                    networkState.postValue(NetworkState.LOADED)
                    detailTvShowViewModel.postValue(it)
                },1000)
            },{
                networkState.postValue(NetworkState.ERROR)
                it.message?.let {
                    Log.e("Error Detail tv ", it)
                }
            })
        compositeDisposable.add(disposable)
        return detailTvShowViewModel
    }

    fun getLoaded() = networkState

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}