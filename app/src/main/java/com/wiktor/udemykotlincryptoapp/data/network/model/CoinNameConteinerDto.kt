package com.wiktor.udemykotlincryptoapp.data.network.model

import com.google.gson.annotations.SerializedName


data class CoinNameConteinerDto(
    @SerializedName("CoinInfo")
    val coinInfo: CoinNameDto? = null,
)