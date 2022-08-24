package com.wiktor.udemykotlincryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wiktor.udemykotlincryptoapp.api.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


//https://min-api.cryptocompare.com/
class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = ApiFactory.apiService.getTopCoinInfo()
            //io.reactivex.rxjava2:rxjava:2.2.8
            //io.reactivex.schedulers.Schedulers
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            //io.reactivex.rxjava2:rxandroid:2.1.1
            //io.reactivex.android.schedulers.AndroidSchedulers
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("qwerty", it.toString())
            }, {
                Log.d("qwerty", it.message.toString())
            })

        compositeDisposable.add(disposable)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}