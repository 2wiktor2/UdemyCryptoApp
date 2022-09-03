package com.wiktor.udemykotlincryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wiktor.udemykotlincryptoapp.data.repository.CoinRepositoryImpl
import com.wiktor.udemykotlincryptoapp.domain.GetCoinInfoListUseCase
import com.wiktor.udemykotlincryptoapp.domain.GetCoinInfoUseCase
import com.wiktor.udemykotlincryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    //Детальная информация по одной валюте
    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    //В обсалютно любом объекте в котлин можно добавить блок инициализации.
    // Код который указан в скобках будет автоматически вазываться при создании этого объекта
    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

}