package com.wiktor.udemykotlincryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.wiktor.udemykotlincryptoapp.data.network.ApiFactory
import com.wiktor.udemykotlincryptoapp.data.database.AppDatabase
import com.wiktor.udemykotlincryptoapp.data.model.CoinPriceInfo
import com.wiktor.udemykotlincryptoapp.data.model.CoinPriceInfoRawData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    //Детальная информация по одной валюте
    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }


    //В обсалютно любом объекте в котлин можно добавить блок инициализации.
    // Код который указан в скобках будет автоматически вазываться при создании этого объекта
    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinInfo(limit = 50)
            //здесь обрабатываем входящий ответ от сервера. Получаем список, обрабатывае его и возвращаем строку.
            // дальше в методе  .subscribe({.... уже работаем с этой строкой
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            // Метод   .flatMap возмет строку из блока  .map     и пересдаст ее в    .flatMap в виде переменной it
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            //Интервал между вызовами   .repeat() или .retry()
            .delaySubscription(10, TimeUnit.SECONDS)
            //бесконечно выполняет загрузку данных. повторяет загрузку пока все происходит успешно. Если произошла ошибка, то загрузка возобновляться не будет.
            .repeat()
            //   .retry() - выполнит загрузку если произошла неуспешная загрузка.
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("qwerty", "Success: $it")
            }, {
                Log.d("qwerty", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData,
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}