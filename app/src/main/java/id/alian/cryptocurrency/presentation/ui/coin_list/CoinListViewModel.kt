package id.alian.cryptocurrency.presentation.ui.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.alian.cryptocurrency.commons.Resource
import id.alian.cryptocurrency.domain.use_case.get_coins.GetCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CoinListState>(CoinListState.Empty)
    val state: StateFlow<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _state.value = CoinListState.Success(result.data)
                    }
                }

                is Resource.Error -> {
                    _state.value =
                        CoinListState.Error(result.message ?: "an expected error occurred")
                }

                is Resource.Loading -> {
                    _state.value = CoinListState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}
