package com.dhani.movieapps.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhani.movieapps.R
import com.dhani.movieapps.adapter.MovieAdapter
import com.dhani.movieapps.adapter.TvShowAdapter
import com.dhani.movieapps.network.response.DataResultTv
import com.dhani.movieapps.network.response.DataResultsMovie
import com.dhani.movieapps.ui.detail.DetailMovieActivity
import com.dhani.movieapps.viewModel.MovieViewModel
import com.dhani.movieapps.viewModel.TvViewModel
import kotlinx.android.synthetic.main.fragment_now_playing.*

/**
 * Directed By Dhani
 */
class MovieFragment(private val type: String) : Fragment() {

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter()
    }

    private val tvShowAdapter: TvShowAdapter by lazy {
        TvShowAdapter()
    }

    private val viewModel: MovieViewModel by viewModels()
    private val viewModelTv: TvViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        rv_movie.layoutManager = LinearLayoutManager(activity)

        rv_movie.isVerticalScrollBarEnabled = false
        rv_movie.setHasFixedSize(true)
        if (type == "movie") {
            rv_movie.adapter = movieAdapter
            initModelMovie()
        } else {
            rv_movie.adapter = tvShowAdapter
            initModelTv()
        }
    }

    private fun initModelMovie() {
        viewModel.getMovie().observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
            Handler().postDelayed({
                rv_movie.scrollToPosition(0)
            }, 800)
        })
        viewModel.getLoader().observe(viewLifecycleOwner, Observer {
            movieAdapter.setNetworkState(it)
        })

        movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(dataMovie: DataResultsMovie) {
                startActivity(Intent(context,DetailMovieActivity::class.java)
                    .putExtra("id",dataMovie.id)
                    .putExtra("type","movie"))
            }

        })
    }

    private fun initModelTv() {
        viewModelTv.getTvShow().observe(viewLifecycleOwner, Observer {
            tvShowAdapter.submitList(it)
            Handler().postDelayed({
                rv_movie.scrollToPosition(0)
            }, 800)
        })

        viewModelTv.getLoader().observe(viewLifecycleOwner, Observer {
            tvShowAdapter.setNetworkState(it)
        })

        tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(dataMovie: DataResultTv) {
                startActivity(Intent(context,DetailMovieActivity::class.java)
                    .putExtra("id",dataMovie.id)
                    .putExtra("type","tvShow"))
            }

        })
    }
}
