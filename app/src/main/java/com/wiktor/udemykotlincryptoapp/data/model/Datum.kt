package com.wiktor.udemykotlincryptoapp.data.model

import com.google.gson.annotations.SerializedName


data class Datum(
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfo? = null
)