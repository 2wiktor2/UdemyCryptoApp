package com.wiktor.udemykotlincryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wiktor.udemykotlincryptoapp.R
import com.wiktor.udemykotlincryptoapp.databinding.ActivityCoinDetailBinding

class ActivityCoinDetail : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
                .commit()
        }

    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        //Нужно передать откуда будем запускать Intent
        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, ActivityCoinDetail::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}