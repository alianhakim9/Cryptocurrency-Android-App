package id.alian.cryptocurrency.domain.repository

import id.alian.cryptocurrency.data.remote.dto.CoinDto
import id.alian.cryptocurrency.data.remote.dto.coin_detail_dto.CoinDetailDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}