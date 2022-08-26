package com.wiktor.udemykotlincryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


//https://min-api.cryptocompare.com/
class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this) {
            Log.d("qwerty", "Success in activity $it")
        }

        viewModel.getDetailInfo("BTC").observe(this, Observer {
            Log.d("qwerty", "Success in activity 2 $it")
        })

    }
}