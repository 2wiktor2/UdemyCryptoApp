package com.wiktor.udemykotlincryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.wiktor.udemykotlincryptoapp.data.database.AppDatabase
import com.wiktor.udemykotlincryptoapp.data.mapper.CoinMapper
import com.wiktor.udemykotlincryptoapp.data.network.ApiFactory
import com.wiktor.udemykotlincryptoapp.domain.CoinInfo
import com.wiktor.udemykotlincryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val apiService = ApiFactory.apiService

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fronSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fronSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            //Получаем топ 50 валют
            val topCoins = apiService.getTopCoinInfo(limit = 50)
            //Преобразование валют в одну строку
            val fSims = mapper.mapNamesListToString(topCoins)
            // По строке fsyms загружаем необходимые данные из сети
            val jsonContainer = apiService.getFullPriceList(fSyms = fSims)
            //Преоборазование jsonContainer в коллекцию объектов Dto
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            //коллекцию объектов Dto в коллекцию объектов бд
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            // Вставка данных в базу
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }
}р