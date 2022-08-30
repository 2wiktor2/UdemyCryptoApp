package com.wiktor.udemykotlincryptoapp.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


// Аналог синглтона
object ApiFactory {
    //https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD
    private const val BASE_URL = " https://min-api.cryptocompare.com/data/"
//    https://www.cryptocompare.com/media/37458963/near.png
    const val BASE_IMAGE_URL = "https://cryptocompare.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}