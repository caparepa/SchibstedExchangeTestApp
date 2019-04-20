package com.serranocjm.schibstedexchangetestapp.extensions

import android.content.Context
import android.text.InputType
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.serranocjm.schibstedexchangetestapp.model.ExchangeRate
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import org.json.JSONObject
import java.util.*

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

//set max min date on picker
fun DatePicker.setMaxMinDate(minDate : Long, maxDate: Long){
    this.minDate= minDate
    this.maxDate = maxDate
}

fun DatePicker.getTimeInMillis() : Long {

    val day = this.dayOfMonth
    val month = this.month
    val year = this.year

    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)

    return calendar.timeInMillis

}

//show toasts!
fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastShort(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()