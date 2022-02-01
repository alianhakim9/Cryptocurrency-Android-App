package id.alian.cryptocurrency.data.remote

import id.alian.cryptocurrency.data.remote.dto.CoinDto
import id.alian.cryptocurrency.data.remote.dto.coin_detail_dto.CoinDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDto

}