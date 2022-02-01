package id.alian.cryptocurrency.data.repository

import id.alian.cryptocurrency.data.remote.CoinPaprikaApi
import id.alian.cryptocurrency.data.remote.dto.CoinDto
import id.alian.cryptocurrency.data.remote.dto.coin_detail_dto.CoinDetailDto
import id.alian.cryptocurrency.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {

    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId = coinId)
    }
}