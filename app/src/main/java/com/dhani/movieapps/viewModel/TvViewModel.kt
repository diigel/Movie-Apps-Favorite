package com.dhani.movieapps.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dhani.movieapps.utils.NetworkState
import com.dhani.movieapps.utils.TvShowFactory
import io.reactivex.disposables.CompositeDisposable

class TvViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var  factory : TvShowFactory? = null
    private var networkState : LiveData<NetworkState> = MutableLiveData()

    init {
        factory= TvShowFactory(compositeDisposable = compositeDisposable)
        networkState = Transformations.switchMap(factory?.dataSource!!){
            it.networkState
        }
    }

    private fun configPaged(): PagedList.Config =
        PagedList.Config.Builder()
            .setPageSize(4)
            .setEnablePlaceholders(true)
            .build()

    fun getTvShow() = LivePagedListBuilder(factory!!,configPaged()).build()

    fun getLoader() = networkState

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}