package com.wiktor.udemykotlincryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.wiktor.udemykotlincryptoapp.data.database.AppDatabase
import com.wiktor.udemykotlincryptoapp.data.mapper.CoinMapper
import com.wiktor.udemykotlincryptoapp.data.network.ApiFactory
import com.wiktor.udemykotlincryptoapp.data.workers.RefreshDataWorker
import com.wiktor.udemykotlincryptoapp.domain.CoinInfo
import com.wiktor.udemykotlincryptoapp.domain.CoinRepository

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }


    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(            RefreshDataWorker.NAME, ExistingWorkPolicy.REPLACE, RefreshDataWorker.makeRequest()
        )
    }
}