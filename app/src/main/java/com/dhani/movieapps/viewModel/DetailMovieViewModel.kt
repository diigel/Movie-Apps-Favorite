package com.dhani.movieapps.viewModel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhani.movieapps.network.Network
import com.dhani.movieapps.network.response.DetailMovie
import com.dhani.movieapps.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val detailMovieLiveData : MutableLiveData<DetailMovie> = MutableLiveData()
    private val netwokState : MutableLiveData<NetworkState> = MutableLiveData()

    fun getDetailMovie(movieId : Int?) : LiveData<DetailMovie>{
        netwokState.postValue(NetworkState.LOADING)
        val disposable = Network.getRoutesRx().getDetailMovie(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Handler().postDelayed({
                    netwokState.postValue(NetworkState.LOADED)
                    if (it != null){
                        detailMovieLiveData.postValue(it)
                    }
                }, 1000)
            },{
                netwokState.postValue(NetworkState.ERROR)
                it.message?.let {
                    Log.e("Error Detail Response" , it)
                }
            })
        compositeDisposable.add(disposable)
        return  detailMovieLiveData
    }

    fun getLoaded() = netwokState

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}