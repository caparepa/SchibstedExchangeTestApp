package com.serranocjm.schibstedexchangetestapp.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RateHistoryInterface {

    @GET("history?start_at={begin_date}&end_at={end_date}&symbols={currency}")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getHistoricRates(
        @Path("start_at") startDate: String,
        @Path("end_at") endDate: String,
        @Path("currency") currency: String
    ): Call<ResponseBody>

}