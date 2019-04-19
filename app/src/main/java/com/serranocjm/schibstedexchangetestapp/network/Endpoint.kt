package com.serranocjm.schibstedexchangetestapp.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    //TODO: Regular Retrofit
    @GET("history")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getHistoricRates(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("currency") currency: String
    ): Call<ResponseBody>
}