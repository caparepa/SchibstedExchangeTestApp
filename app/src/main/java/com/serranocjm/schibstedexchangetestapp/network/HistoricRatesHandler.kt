package com.serranocjm.schibstedexchangetestapp.network

import android.content.Context
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import retrofit2.Callback

object HistoricRatesHandler {

    private val retroBase =
        RetroBase(Url.BASE_URL)

    fun getRates(
        startDate: String,
        endDate: String,
        base: String,
        target: String,
        ctx: Context,
        callBack: Callback<HistoryExchangeRate>
    ) {
        val service = retroBase.retrofit.create(Endpoint::class.java)
        service.getHistoricRatesPrime(startDate, endDate, base, target).enqueue(callBack)
    }

}