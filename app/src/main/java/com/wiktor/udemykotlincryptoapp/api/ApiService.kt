package com.wiktor.udemykotlincryptoapp.api

import com.wiktor.udemykotlincryptoapp.pojo.CoinInfoListOfData
import com.wiktor.udemykotlincryptoapp.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


// здесь хронятся все методы по работе с сетью
interface ApiService {

    //получаем список самых популярных валют
    // возврощаемый тип - Single т.к. мы используем rxJava для получения данных
    // Объект который мы хотим получить на выходе - CoinInfoListOfData
    //https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD
    // endPoint - top/totalvolfull
    @GET("top/totalvolfull")
    fun getTopCoinInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY,
    ): Single<CoinInfoListOfData>

    // Метод для получения полной информации
    //    https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD
    // endPoint - pricemultifull
    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
    ): Single<CoinPriceInfoRawData>

    //Константы. Выносим в companion object
    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
    }
}