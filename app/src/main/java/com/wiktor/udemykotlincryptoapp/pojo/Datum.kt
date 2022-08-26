package com.wiktor.udemykotlincryptoapp.pojo

import com.google.gson.annotations.SerializedName


data class Datum(
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfo? = null
)