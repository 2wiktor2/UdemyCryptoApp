package com.wiktor.udemykotlincryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.wiktor.udemykotlincryptoapp.data.database.AppDatabase
import com.wiktor.udemykotlincryptoapp.data.mapper.CoinMapper
import com.wiktor.udemykotlincryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters){
    private val apiService = ApiFactory.apiService

    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val mapper = CoinMapper()
    override suspend fun doWork(): Result {
        while (true) {
            try {//Получаем топ 50 валют
                val topCoins = apiService.getTopCoinInfo(limit = 50)
                //Преобразование валют в одну строку
                val fSims = mapper.mapNamesListToString(topCoins)
                // По строке fSyms загружаем необходимые данные из сети
                val jsonContainer = apiService.getFullPriceList(fSyms = fSims)
                //Преоборазование jsonContainer в коллекцию объектов Dto
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                //коллекцию объектов Dto в коллекцию объектов бд
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                // Вставка данных в базу
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object{
       const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }

}