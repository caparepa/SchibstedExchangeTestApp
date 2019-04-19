package com.serranocjm.schibstedexchangetestapp

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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
import com.serranocjm.schibstedexchangetestapp.model.ExchangeRate
import com.serranocjm.schibstedexchangetestapp.model.HistoryExchangeRate
import com.serranocjm.schibstedexchangetestapp.network.HistoricRatesHandler.getRates
import com.serranocjm.schibstedexchangetestapp.network.HistoricRatesResponseHandler
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {



    lateinit var ctx: Context
    lateinit var queryButton : Button
    lateinit var chart: LineChart
    lateinit var dataSet: LineDataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ctx = this
        queryButton = bt_query_button
        chart = lc_rate_chart
        initChart()


        queryButton.setOnClickListener {
            getHistoric()
        }
    }

    override fun onNothingSelected() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getHistoric() {
        getRates("2018-02-02", "2018-03-03", "EUR", "USD", this, object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                //
                Log.d("TAG", "FAIL")
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                //
                Log.d("TAG", "SUCCESS")
                val obj : HistoryExchangeRate? = HistoricRatesResponseHandler.processHistoricRates(response)
                Toast.makeText(ctx, "HEY!", Toast.LENGTH_LONG).show()
                if(obj != null) {
                    //populateView(obj)
                    setChart(obj)
                }
            }
        })
    }

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

    private fun setChart(obj: HistoryExchangeRate) {

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
            yAxis.axisMaximum = 3f
            yAxis.axisMinimum = 0f
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

    private fun populateView(obj : HistoryExchangeRate) {

        if(obj.rateList.isNotEmpty()){
            populateChart(obj.rateList)
        }

    }

    private fun populateChart(rateList: List<ExchangeRate>) {

        val set1 : LineDataSet
        val values = ArrayList<Entry>()
        val dates = ArrayList<String>()

        //set entry list
        for(i in rateList.indices){
            values.add(Entry(i.toFloat(),rateList[i].rate.toFloat()))
            dates.add(rateList[i].date)
        }


        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            // set color of filled area

                set1.fillColor = Color.BLACK

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(dates)

            chart.data = data
        }

    }
}
