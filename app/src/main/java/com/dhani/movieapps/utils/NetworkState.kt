package com.dhani.movieapps.utils

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    ENDOFPAGE
}


class NetworkState(val status: Status,val message: String) {

    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Berhasil")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Berjalan")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Maaf Terjadi Kesalahan !")
        val END_OF_PAGE: NetworkState = NetworkState(Status.ENDOFPAGE, "Udah semuanya nih !.")
    }
}