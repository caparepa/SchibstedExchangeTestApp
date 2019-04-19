package com.serranocjm.schibstedexchangetestapp.network

import com.serranocjm.schibstedexchangetestapp.extensions.processRateResponse
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class HistoricRatesResponseHandler {

    companion object {

        fun processHistoricRates(response: Response<ResponseBody>?) : HistoryExchangeRate? {
            if (response?.code() == 200) {
                val data = JSONObject(response?.body()?.string())
                return data.processRateResponse()
            }
            return null
        }
    }

}