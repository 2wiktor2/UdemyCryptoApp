package com.wiktor.udemykotlincryptoapp.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class CoinInfo(
    @SerializedName("Name")
    var name: String? = null
)