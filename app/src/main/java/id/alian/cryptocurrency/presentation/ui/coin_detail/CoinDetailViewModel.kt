package id.alian.cryptocurrency.presentation.ui.coin_detail

import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.cryptocurrency.commons.Resource
import id.alian.cryptocurrency.domain.use_case.get_coin.GetCoinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<CoinDetailState>(CoinDetailState.Empty)
    val state = _state

    init {
        val args = CoinDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
        Log.d("CoinDetailViewModel", "Init ViewModel: ${args.coinId}")
        getCoin(args.coinId)
    }

    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        if (result.data.isActive) {
                            _state.value = CoinDetailState.Success(
                                result.data,
                                "Active",
                                GREEN
                            )
                        } else {
                            _state.value = CoinDetailState.Success(
                                result.data,
                                "InActive",
                                RED
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _state.value = CoinDetailState.Loading
                }

                is Resource.Error -> {
                    _state.value =
                        CoinDetailState.Error(result.message ?: "An expected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}