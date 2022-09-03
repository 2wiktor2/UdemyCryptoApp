package com.wiktor.udemykotlincryptoapp.data.mapper

import com.google.gson.Gson
import com.wiktor.udemykotlincryptoapp.data.database.CoinInfoDbModel
import com.wiktor.udemykotlincryptoapp.data.network.model.CoinInfoDto
import com.wiktor.udemykotlincryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.wiktor.udemykotlincryptoapp.data.network.model.CoinNamesListDto
import com.wiktor.udemykotlincryptoapp.domain.CoinInfo
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    companion object{
        //    https://www.cryptocompare.com/media/37458963/near.png
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }

    //Преобразование класса Dto в класс базы данных
    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel {
        return CoinInfoDbModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lastUpdate = dto.lastUpdate,
            highDay = dto.highDay,
            lowDay = dto.lowDay,
            lastMarket = dto.lastMarket,
            imageUrl = BASE_IMAGE_URL + dto.imageUrl
        )
    }

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinInfo?.name }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimeStampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    private fun convertTimeStampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(date)
    }

}