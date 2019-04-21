package com.serranocjm.schibstedexchangetestapp.model

import com.google.gson.annotations.SerializedName

/**
 * TODO: perhaps this'll be modified to add all currencies, or will be foregone of in favour of a nested TreeMap
 */
data class Rate (
    @field:SerializedName("USD")
    val usd: Double? = null
)