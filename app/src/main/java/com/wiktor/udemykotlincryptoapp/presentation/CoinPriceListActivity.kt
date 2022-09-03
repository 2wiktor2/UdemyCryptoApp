package com.wiktor.udemykotlincryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wiktor.udemykotlincryptoapp.databinding.ActivityCoinPriceListBinding
import com.wiktor.udemykotlincryptoapp.domain.CoinInfo
import com.wiktor.udemykotlincryptoapp.presentation.adapters.CoinInfoAdapter


//https://min-api.cryptocompare.com/
class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {

            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                Log.d("qwertyu", coinPriceInfo.fromSymbol)

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
        binding.recyclerViewCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            // Log.d("qwerty", "Success in activity $it")
            adapter.coinListInfo = it
        }
    }
}