package com.wiktor.udemykotlincryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.wiktor.udemykotlincryptoapp.domain.CoinInfo

object CoinInfoDiffCallBack : DiffUtil.ItemCallback<CoinInfo>() {

    // Метод areItemsTheSame проверяет что это один и тот же объект
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    // Метод areContentsTheSame проверяет не изменилось ли содержимое объекта CoinInfo
    // Если содержимое изменилось, то его нужно перерисовать
    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }

}