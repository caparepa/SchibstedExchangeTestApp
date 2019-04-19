package com.serranocjm.schibstedexchangetestapp.model

class HistoryExchangeRate(
    val baseCurrency: String,
    val targetCurrency: String,
    val startDate: String,
    val endDate: String,
    val rateList: List<ExchangeRate>
)