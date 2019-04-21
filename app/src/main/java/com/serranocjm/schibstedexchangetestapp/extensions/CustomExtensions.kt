package com.serranocjm.schibstedexchangetestapp.extensions

import android.content.Context
import android.text.InputType
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import java.util.*

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