package com.serranocjm.schibstedexchangetestapp.network

import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRatePrime
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @GET("history")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getHistoricRates(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Call<ResponseBody>

    @GET("history")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getHistoricRatesPrime(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Call<HistoryExchangeRatePrime>
}