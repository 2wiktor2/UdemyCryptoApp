package com.wiktor.udemykotlincryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wiktor.udemykotlincryptoapp.R
import com.wiktor.udemykotlincryptoapp.presentation.adapters.CoinInfoAdapter
import com.wiktor.udemykotlincryptoapp.data.model.CoinPriceInfo


//https://min-api.cryptocompare.com/
class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
//            Log.d("qwertyu", coinPriceInfo.fromSymbol)

                // Старый способ запуска активити
/*                val intent = Intent(this@CoinPriceListActivity, ActivityCoinDetail::class.java)
                intent.putExtra(ActivityCoinDetail.EXTRA_FROM_SYMBOL, coinPriceInfo.fromSymbol)
                startActivity(intent)*/

                // Новый способ запуска активити
                val intent = ActivityCoinDetail.newIntent(this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol)
                startActivity(intent)

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