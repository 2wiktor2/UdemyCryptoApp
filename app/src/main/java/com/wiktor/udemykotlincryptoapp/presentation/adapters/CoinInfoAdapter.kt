package com.wiktor.udemykotlincryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.wiktor.udemykotlincryptoapp.R
import com.wiktor.udemykotlincryptoapp.databinding.ItemCoinInfoBinding
import com.wiktor.udemykotlincryptoapp.domain.CoinInfo


class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallBack) {



    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
/*        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)*/
        val binding =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)

        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)

        with(holder.binding) {
            textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            textViewPrice.text = coin.price
            textViewLastUpdate.text = String.format(lastUpdateTemplate,
                coin.lastUpdate)
            Picasso
                .get()
                .load(coin.imageUrl)
//                .resize(30, 30)
//                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(this.imageViewLogoCoin)


            root.setOnClickListener {
                onCoinClickListener?.onCoinClick(coin)
            }
        }
    }


    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}