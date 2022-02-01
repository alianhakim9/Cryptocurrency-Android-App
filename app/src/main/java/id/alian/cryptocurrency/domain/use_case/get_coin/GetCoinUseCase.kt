package id.alian.cryptocurrency.domain.use_case.get_coin

import android.util.Log
import id.alian.cryptocurrency.commons.Resource
import id.alian.cryptocurrency.data.remote.dto.coin_detail_dto.toCoin
import id.alian.cryptocurrency.domain.model.CoinDetail
import id.alian.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId = coinId)
            Log.d("CoinUseCase", "invoke: $coin")
            emit(Resource.Success<CoinDetail>(coin.toCoin()))
        } catch (e: HttpException) {
            emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<CoinDetail>("Couldn't reach server. Check your internet connection"))
        }
    }
}