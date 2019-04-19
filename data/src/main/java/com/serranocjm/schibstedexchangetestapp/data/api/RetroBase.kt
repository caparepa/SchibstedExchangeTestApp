package com.serranocjm.schibstedexchangetestapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetroBase(url: String) {
    val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(ScalarsConverterFactory.create()).build()
}