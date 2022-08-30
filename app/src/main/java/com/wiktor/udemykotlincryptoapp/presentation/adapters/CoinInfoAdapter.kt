package com.wiktor.udemykotlincryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wiktor.udemykotlincryptoapp.R
import com.wiktor.udemykotlincryptoapp.data.model.CoinPriceInfo

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinListInfo: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinListInfo.get(position)

        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)

        with(holder) {
            textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            textViewPrice.text = coin.price
            textViewLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
            Picasso
                .get()
                .load(coin.getFullImageUrl())
//                .resize(30, 30)
//                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(this.imageViewLogoCoin)
        }
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    override fun getItemCount(): Int = coinListInfo.size

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewLogoCoin: ImageView =
            itemView.findViewById<ImageView>(R.id.imageView_logo_coin)
        val textViewSymbols = itemView.findViewById<TextView>(R.id.textView_symbols)
        val textViewPrice = itemView.findViewById<TextView>(R.id.textView_price)
        val textViewLastUpdate = itemView.findViewById<TextView>(R.id.textView_last_update)
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}