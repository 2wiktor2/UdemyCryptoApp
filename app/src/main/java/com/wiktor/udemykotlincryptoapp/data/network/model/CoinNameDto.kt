package com.wiktor.udemykotlincryptoapp.data.network.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class CoinNameDto(
    @SerializedName("Name")
    var name: String? = null,
)