package com.serranocjm.schibstedexchangetestapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.serranocjm.schibstedexchangetestapp.network.HistoricRatesHandler
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            getHistoric()
        }
    }

    fun getHistoric() {
        HistoricRatesHandler.getRates("2018-02-02","2018-03-03","USD,EUR", this, object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                //
                Log.d("TAG", "FAIL")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                //
                Log.d("TAG", "SUCCESS")
            }
        })
    }
}
