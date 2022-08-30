package com.wiktor.udemykotlincryptoapp.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoRawData(
    @SerializedName("RAW")
    var coinPriceInfoJsonObject: JsonObject? = null
)
