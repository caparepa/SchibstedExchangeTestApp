package com.serranocjm.schibstedexchangetestapp.extensions

import org.json.JSONObject



fun JSONObject.processRateResponse() {

    val rates = this.getJSONObject("rates")
    val keys = rates.keys()

    while (keys.hasNext()) {
        // loop to get the dynamic key
        val currentDynamicKey = keys.next() as String

        // get the value of the dynamic key
        val currentDynamicValue = rates.getJSONObject(currentDynamicKey)

        // do something here with the value...
    }
}