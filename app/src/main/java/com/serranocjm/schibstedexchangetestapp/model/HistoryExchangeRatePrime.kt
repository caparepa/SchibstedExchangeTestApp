package com.serranocjm.schibstedexchangetestapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

class HistoryExchangeRatePrime  (
    @field:SerializedName("base")
    val baseCurrency: String,
    @field:SerializedName("start_at")
    val startDate: String,
    @field:SerializedName("end_at")
    val endDate: String,
    @field:SerializedName("rates")
    val rateList: TreeMap<String, TreeMap<String, Double>>
)