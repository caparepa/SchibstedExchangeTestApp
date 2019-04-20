package com.serranocjm.schibstedexchangetestapp.extensions

import android.text.InputType
import android.widget.EditText
import com.serranocjm.schibstedexchangetestapp.model.ExchangeRate
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import org.json.JSONObject

//TODO: make this better!
fun JSONObject.processRateResponse() : HistoryExchangeRate {

    val rates = this.getJSONObject("rates")
    val keys = rates.keys()

    var ratesList : MutableList<ExchangeRate> = ArrayList<ExchangeRate>()

    justTry {
        while (keys.hasNext()) {
            // loop to get the dynamic key
            val rateDateKey = keys.next() as String

            // get the value of the dynamic key
            val rateDateValue = rates.getJSONObject(rateDateKey)

            //get the actual rate value
            val rateValue = rateDateValue.get("USD") as Double

            //create an exchange rate object
            val exchangeRateObject = ExchangeRate(rateDateKey, rateValue)

            //add to list
            ratesList.add(exchangeRateObject)
        }
    }

    //captain obvious to the rescue
    val baseCurrency = this.getString("base")
    val startDate = this.getString("start_at")
    val endDate = this.getString("end_at")

    return HistoryExchangeRate(baseCurrency, "USD", startDate, endDate, ratesList)
}

//try-catch wrapper extension
inline fun <T> justTry(block: () -> T) = try { block() } catch (e: Throwable) {}


//read-only edittext
fun EditText.setReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}