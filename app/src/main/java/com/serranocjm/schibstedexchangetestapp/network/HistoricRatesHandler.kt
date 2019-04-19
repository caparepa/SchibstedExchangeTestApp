package com.serranocjm.schibstedexchangetestapp.network

import android.content.Context
import okhttp3.ResponseBody
import retrofit2.Callback

object HistoricRatesHandler {

    val retroBase =
        RetroBase(Url.BASE_URL)

    fun getRates(
        startDate: String,
        endDate: String,
        base: String,
        target: String,
        ctx: Context,
        callBack: Callback<ResponseBody>
    ) {
        val serv = retroBase.retrofit.create(Endpoint::class.java)
        serv.getHistoricRates(startDate, endDate, base, target).enqueue(callBack)
    }

}