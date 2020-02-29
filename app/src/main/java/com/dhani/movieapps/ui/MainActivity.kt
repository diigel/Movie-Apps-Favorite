package com.dhani.movieapps.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.dhani.movieapps.R
import com.dhani.movieapps.fragment.MovieFragment
import com.dhani.movieapps.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        vp_main.offscreenPageLimit = 2
        ViewPagerAdapter(supportFragmentManager)
            .also { pagerAdapter ->
                pagerAdapter.addFragment(MovieFragment("movie"), "Movie")
                pagerAdapter.addFragment(MovieFragment("tv_show"), "Tv Show")
                vp_main.adapter = pagerAdapter
        }
        tab.setupWithViewPager(vp_main)
        vp_main.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab))
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                vp_main.currentItem = tab?.position!!
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {}
        })
    }


    override fun onBackPressed() {
        dialogClose()
    }

    private fun dialogClose() {
        val dialog = AlertDialog.Builder(this)
        val builder = dialog
            .setTitle("Keluar")
            .setCancelable(true)
            .setMessage("Apakah Anda Ingin Keluar")
            .setNegativeButton("Tidak") { view, i ->
                view.dismiss()
            }
            .setPositiveButton("Ya") { view, i ->
                view.dismiss()
                finishAffinity()
            }
            .create()
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.show()
    }
}
