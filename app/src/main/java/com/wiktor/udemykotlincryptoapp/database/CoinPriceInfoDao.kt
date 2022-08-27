package com.wiktor.udemykotlincryptoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wiktor.udemykotlincryptoapp.pojo.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {
    //выводит список валют в recyclerView
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinPriceInfo>>

    //Детальная информация для отдельного экрана
    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo>

    //Сохранение, полученных из итернета, данных в базу
    // onConflict = OnConflictStrategy.REPLACE - заменять данные при получении новых данных
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinPriceInfo>)
}