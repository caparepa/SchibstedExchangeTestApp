package com.serranocjm.schibstedexchangetestapp.network

import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Endpoint {

    @GET("history")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getHistoricRates(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Call<HistoryExchangeRate>

    @GET("history")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getHistoricRatesCoroutine(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Deferred<Response<HistoryExchangeRate>>
}