package com.serranocjm.schibstedexchangetestapp.network

import android.content.Context
import com.serranocjm.schibstedexchangetestapp.extensions.justTry
import com.serranocjm.schibstedexchangetestapp.extensions.toastLong
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import kotlinx.coroutines.*
import retrofit2.Callback

object HistoricRatesHandler {

    val retroBase =
        RetroBase(Url.BASE_URL)

}