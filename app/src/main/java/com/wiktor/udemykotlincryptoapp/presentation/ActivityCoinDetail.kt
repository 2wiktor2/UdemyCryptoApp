package com.wiktor.udemykotlincryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.wiktor.udemykotlincryptoapp.R

class ActivityCoinDetail : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, Observer {
//                Log.d("qwertyu", it.toString())
                val textViewPrice = findViewById<TextView>(R.id.tvPrice)
                textViewPrice.text = it.price
                val textViewMinPrice = findViewById<TextView>(R.id.tvMinPrice)
                textViewMinPrice.text = it.lowDay
                val textViewMaxPrice = findViewById<TextView>(R.id.tvMaxPrice)
                textViewMaxPrice.text = it.highDay
                val textViewLasMarket = findViewById<TextView>(R.id.tvLastMarket)
                textViewLasMarket.text = it.lastMarket
                val textViewLastUpdate = findViewById<TextView>(R.id.tvLastUpdate)
                textViewLastUpdate.text = it.getFormattedTime()
                val textViewFromSymbol = findViewById<TextView>(R.id.tvFromSymbol)
                textViewFromSymbol.text = it.fromSymbol
                val textViewToSymbol = findViewById<TextView>(R.id.tvToSymbol)
                textViewToSymbol.text = it.toSymbol
                val imageViewLogoCoin = findViewById<ImageView>(R.id.ivLogoCoin)
                Picasso.get().load(it.getFullImageUrl()).into(imageViewLogoCoin)
            })
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        //Нужно передать откуда будем запускать Intent
        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, ActivityCoinDetail::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}