package com.serranocjm.schibstedexchangetestapp

import android.content.Context
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response

class HistoricRatesResponseHandler {

    fun processHistoricRates(response: Response<ResponseBody>?) {
        if (response?.code() == 200) {
            val data = JSONObject(response?.body()?.string())
            val array:String=data.getJSONArray("data").toString()
            val gson = Gson()
        }
    }

}