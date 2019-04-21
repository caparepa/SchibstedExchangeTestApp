package com.serranocjm.schibstedexchangetestapp

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.icu.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.serranocjm.schibstedexchangetestapp.custom.MyMarkerView
import com.serranocjm.schibstedexchangetestapp.extensions.*
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRatePrime
import com.serranocjm.schibstedexchangetestapp.model.Rate
import com.serranocjm.schibstedexchangetestapp.network.HistoricRatesHandler.getRates
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var ctx: Context
    lateinit var queryButton : Button
    lateinit var chart: LineChart
    lateinit var dataSet: LineDataSet
    lateinit var startDateEditText: EditText
    lateinit var endDateEditText: EditText
    lateinit var startCalendar : DatePickerDialog
    lateinit var endCalendar : DatePickerDialog

    var startDate : String = "2019-04-01"
    var endDate : String = "2019-04-08"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ctx = this

        initElements()
        setListeners()
        initChart()


    }

    override fun onNothingSelected() {
        //TODO("not implemented")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        //TODO("not implemented")
    }

    private fun initElements() {
        queryButton = bt_query_button
        chart = lc_rate_chart
        startDateEditText = et_start_date
        endDateEditText = et_end_date
        startDateEditText.setText(startDate)
        endDateEditText.setText(endDate)
    }

    //TODO: clean this code!
    private fun setCalendars(){

        //Today's calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Calendar to set EUR start date
        val minCalendar = Calendar.getInstance()
        minCalendar.set(Calendar.YEAR, 1999)
        minCalendar.set(Calendar.MONTH, 1)
        minCalendar.set(Calendar.DAY_OF_YEAR, 1)

        //Min and max date values
        val maxDate = c.timeInMillis
        val minDate = minCalendar.timeInMillis

        startCalendar = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            //Simple validation
            if(startCalendar.datePicker.getTimeInMillis() > endCalendar.datePicker.getTimeInMillis()) {
                this.toastLong("Start date cannot be higher than end date!")
                startDate = ""
            } else {
                startDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                startDateEditText.setText(startDate)
            }
        }, year, month, day)

        endCalendar = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            //Simple validation
            if(endCalendar.datePicker.getTimeInMillis() < startCalendar.datePicker.getTimeInMillis()) {
                this.toastLong("End date cannot be lower than start date!")
                endDate = ""
            } else {
                endDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                endDateEditText.setText(endDate)
            }
        }, year, month, day)

        //Set bot calendar min and max dates
        startCalendar.datePicker.setMaxMinDate(minDate, maxDate)
        endCalendar.datePicker.setMaxMinDate(minDate, maxDate)
    }

    private fun setListeners() {
        startDateEditText.setReadOnly(true, InputType.TYPE_NULL)
        endDateEditText.setReadOnly(true, InputType.TYPE_NULL)

        setCalendars()

        startDateEditText.setOnClickListener {
            startCalendar.show()
        }

        endDateEditText.setOnClickListener {
            endCalendar.show()
        }

        queryButton.setOnClickListener {
            if(!startDate.isEmpty() && !endDate.isEmpty()) {
                getHistoric()
            } else {
                this.toastLong("You have to select both start and end date!")
            }
        }
    }

    private fun getHistoric() {

        getRates(startDate, endDate, "EUR", "USD", this, object : Callback<HistoryExchangeRatePrime> {
            override fun onFailure(call: Call<HistoryExchangeRatePrime>?, t: Throwable?) {
                //
                Log.d("TAG", "ERROR")
            }

            override fun onResponse(call: Call<HistoryExchangeRatePrime>?, response: Response<HistoryExchangeRatePrime>?) {

                if(response?.body() != null) {
                    val obj : HistoryExchangeRatePrime? = response.body() //IT WORKS, DAMMIT! :D
                    setChart(obj!!)
                } else {
                    ctx.toastLong("NO DATA!")
                }
            }
        })
    }

    //initialize chart styles and stuff
    private fun initChart() {
        run {
            // // Chart Style // //

            // background color
            chart.setBackgroundColor(Color.WHITE)

            // disable description text
            chart.description.isEnabled = false

            // enable touch gestures
            chart.setTouchEnabled(true)

            // set listeners
            chart.setOnChartValueSelectedListener(this)
            chart.setDrawGridBackground(false)

            // create marker to display box when values are selected
            val mv = MyMarkerView(this, R.layout.custom_markerview)

            // Set the marker to the chart
            mv.chartView = chart
            chart.marker = mv

            // enable scaling and dragging
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true)
        }
    }

    //set the chart and other stuff
    private fun setChart(obj: HistoryExchangeRatePrime) {

        val xAxis: XAxis
        run {
            // // X-Axis Style // //
            xAxis = chart.xAxis

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)
        }

        val yAxis: YAxis
        run {
            // // Y-Axis Style // //
            yAxis = chart.axisLeft

            // disable dual axis (only use LEFT axis)
            chart.axisRight.isEnabled = false

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.axisMaximum = 1.8f
            yAxis.axisMinimum = 0.5f
        }

        // add data
        if(obj.rateList.isNotEmpty()){
            populateChart(obj.rateList)
        }

        // draw points over time
        chart.animateX(1500)

        // get the legend (only possible after setting data)
        val l = chart.legend

        // draw legend entries as lines
        l.form = Legend.LegendForm.LINE
    }

    //populate the chart (well duh!)
    private fun populateChart(rateList : TreeMap<String, Rate>) {

        val values = ArrayList<Entry>()
        val dates = ArrayList<String>()
        var i=0

        //get rates and dates from map
        rateList.forEach{ (date, rateValue) ->
            values.add(Entry(i.toFloat(), rateValue.usd!!.toFloat()))
            dates.add(date)
            i++
        }

        if (chart.data != null && chart.data.dataSetCount > 0) {
            dataSet = chart.data.getDataSetByIndex(0) as LineDataSet
            dataSet.values = values
            dataSet.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            dataSet = LineDataSet(values, "DataSet 1")

            dataSet.setDrawIcons(false)

            // draw dashed line
            dataSet.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            dataSet.color = Color.BLACK
            dataSet.setCircleColor(Color.BLACK)

            // line thickness and point size
            dataSet.lineWidth = 1f
            dataSet.circleRadius = 3f

            // draw points as solid circles
            dataSet.setDrawCircleHole(false)

            // customize legend entry
            dataSet.formLineWidth = 1f
            dataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            dataSet.formSize = 15f

            // text size of values
            dataSet.valueTextSize = 9f

            // draw selection line as dashed
            dataSet.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            dataSet.setDrawFilled(true)
            dataSet.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            // set color of filled area

            dataSet.fillColor = Color.BLACK

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(dataSet) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(dates)

            chart.data = data
        }
    }

}
