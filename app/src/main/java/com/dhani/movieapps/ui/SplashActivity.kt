package com.dhani.movieapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhani.movieapps.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        compositeDisposable.delay(2000){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun CompositeDisposable.delay(long: Long, intent: () -> Unit) {
        val disposable = Observable.just(long)
            .subscribeOn(Schedulers.io())
            .delay(long, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                intent.invoke()
            },{
                it.printStackTrace()
            })
        add(disposable)
    }
}
