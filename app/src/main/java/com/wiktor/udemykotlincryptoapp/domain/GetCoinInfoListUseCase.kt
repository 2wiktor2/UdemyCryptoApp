package com.wiktor.udemykotlincryptoapp.domain


class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getCoinInfoList()
}
