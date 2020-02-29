package com.dhani.movieapps.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dhani.movieapps.BuildConfig
import com.dhani.movieapps.R
import com.dhani.movieapps.network.response.DetailMovie
import com.dhani.movieapps.network.response.DetailTvShow
import com.dhani.movieapps.utils.NetworkState
import com.dhani.movieapps.utils.Status
import com.dhani.movieapps.viewModel.DetailMovieViewModel
import com.dhani.movieapps.viewModel.DetailTvShowViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMovieActivity : AppCompatActivity() {

    private val viewModelMovie: DetailMovieViewModel by viewModels()
    private val viewModelTvShow: DetailTvShowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getType()
        initToolbar()
        if (getType().equals("movie")){
            initModelMovie()
        }else {
            initModelTv()
        }

    }

    private fun getValue() = intent?.getIntExtra("id", 0)
    private fun getType() = intent?.getStringExtra("type")

    private fun initToolbar() {
        toobar_detail.setNavigationIcon(R.drawable.ic_back_white)
        toobar_detail.setNavigationOnClickListener { onBackPressed() }
        toobar_detail.title = "Detail"
        toobar_detail.setTitleTextColor(Color.WHITE)
    }

    private fun initView(detailMovie: DetailMovie) {
        Glide.with(this).load(BuildConfig.IMG_URL + detailMovie.posterPath).into(img_poster_detail)
        Glide.with(this).load(BuildConfig.IMG_URL + detailMovie.backdropPath).into(img_detail_back)
        txt_title.text = detailMovie.originalTitle
        txt_user_score.text = detailMovie.voteCount.toString()
        txt_rate.text = detailMovie.voteAverage.toString()
        txt_release.text = detailMovie.releaseDate
        txt_description.text = detailMovie.overview
    }

    private fun initViewTvShow(detailTvShow: DetailTvShow){
        Glide.with(this).load(BuildConfig.IMG_URL + detailTvShow.posterPath).into(img_poster_detail)
        Glide.with(this).load(BuildConfig.IMG_URL + detailTvShow.backdropPath).into(img_detail_back)
        txt_title.text = detailTvShow.originalName
        txt_user_score.text = detailTvShow.voteCount.toString()
        txt_rate.text = detailTvShow.voteAverage.toString()
        txt_release.text = detailTvShow.firstAirDate
        txt_description.text = detailTvShow.overview
    }

    private fun initModelTv(){
        viewModelTvShow.getTvShow(getValue()).observe(this, Observer {
            initViewTvShow(it)
        })

        viewModelTvShow.getLoaded().observe(this, Observer {
            setNetworkState(it)
        })
    }
    private fun initModelMovie() {
        viewModelMovie.getDetailMovie(getValue()).observe(this, Observer {
            initView(it)

        })
        viewModelMovie.getLoaded().observe(this, Observer {
            setNetworkState(it)
        })
    }

    private fun setNetworkState(networkState: NetworkState){
        when (networkState.status) {
            Status.RUNNING -> {
                network_state.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                network_state.visibility = View.GONE
            }
            Status.FAILED -> {
                Toast.makeText(this,networkState.message,Toast.LENGTH_SHORT).show()
                network_state.visibility = View.GONE
            }
            else -> {
                network_state.visibility = View.GONE
                text_message.text = networkState.message
            }
        }
    }
}
