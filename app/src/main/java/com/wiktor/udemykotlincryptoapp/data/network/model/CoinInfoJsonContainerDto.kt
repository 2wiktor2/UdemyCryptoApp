package com.wiktor.udemykotlincryptoapp.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonContainerDto(
    @SerializedName("RAW")
    var json: JsonObject? = null,
)
