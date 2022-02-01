package id.alian.cryptocurrency.presentation.ui.coin_list

import id.alian.cryptocurrency.domain.model.Coin

sealed class CoinListState {
    data class Success(val coins: List<Coin>) : CoinListState()
    data class Error(val message: String) : CoinListState()
    object Loading : CoinListState()
    object Empty : CoinListState()
}
