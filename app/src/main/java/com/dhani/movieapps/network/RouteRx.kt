package com.dhani.movieapps.network

import com.dhani.movieapps.BuildConfig
import com.dhani.movieapps.network.response.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RouteRx {

    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language : String = "id-ID",
        @Query("page")page : Int
    ) : Observable<Movie>

    @GET("tv/popular")
    fun getTvShow(
        @Query("api_key")apiKey: String = BuildConfig.API_KEY,
        @Query("language") language : String = "id-ID",
        @Query("page")page : Int
    ) : Observable<TvShow>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path(value = "movie_id") movieId : Int?,
        @Query("api_key")apiKey: String = BuildConfig.API_KEY,
        @Query("language") language : String = "id-ID"
    ) : Observable<DetailMovie>

    @GET("tv/{tv_id}")
    fun getDetailTvShow(
        @Path(value = "tv_id") tvShowId : Int?,
        @Query("api_key")apiKey: String = BuildConfig.API_KEY,
        @Query("language") language : String = "id-ID"
    ) : Observable<DetailTvShow>
}