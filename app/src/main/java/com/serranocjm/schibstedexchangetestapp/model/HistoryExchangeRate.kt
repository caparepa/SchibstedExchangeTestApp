package com.serranocjm.schibstedexchangetestapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * TODO: check if it's worth the time to leave rateList as is since it can be modified to a nested TreeMap
 * TODO: like TreeMap<String, TreeMap<String, Double>>. It can handle more currencies, but it'll be a hassle to
 * TODO: process later...
 */
class HistoryExchangeRate  (
    @field:SerializedName("base")
    val baseCurrency: String,
    @field:SerializedName("start_at")
    val startDate: String,
    @field:SerializedName("end_at")
    val endDate: String,
    @field:SerializedName("rates")
    val rateList: TreeMap<String, Rate>
)