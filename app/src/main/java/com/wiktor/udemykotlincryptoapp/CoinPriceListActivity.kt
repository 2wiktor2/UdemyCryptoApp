package com.wiktor.udemykotlincryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wiktor.udemykotlincryptoapp.adapters.CoinInfoAdapter
import com.wiktor.udemykotlincryptoapp.pojo.CoinPriceInfo


//https://min-api.cryptocompare.com/
class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
            Log.d("qwertyu", coinPriceInfo.fromSymbol)
            }

        }
        val recyclerViewCoinPriceList =
            findViewById<RecyclerView>(R.id.recyclerView_coin_price_list)
        recyclerViewCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this) {
            // Log.d("qwerty", "Success in activity $it")
            adapter.coinListInfo = it
        }

        viewModel.getDetailInfo("BTC").observe(this, Observer {
            // Log.d("qwerty", "Success in activity 2 $it")
        })

    }
}