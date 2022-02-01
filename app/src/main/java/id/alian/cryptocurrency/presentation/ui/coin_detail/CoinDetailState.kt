package id.alian.cryptocurrency.presentation.ui.coin_detail

import id.alian.cryptocurrency.domain.model.CoinDetail

sealed class CoinDetailState {
    data class Success(
        val coin: CoinDetail,
        val status: String,
        val statusColor: Int,
    ) :
        CoinDetailState()

    data class Error(val message: String) : CoinDetailState()
    object Loading : CoinDetailState()
    object Empty : CoinDetailState()
}
