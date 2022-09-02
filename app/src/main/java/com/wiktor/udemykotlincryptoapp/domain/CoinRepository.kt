package com.wiktor.udemykotlincryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>
    fun getCoinInfo(fronSymbol: String): LiveData<CoinInfo>

    //Метод загрузки данных из сети
   suspend fun loadData()

}