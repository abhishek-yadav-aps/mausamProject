package com.abhitom.mausamproject.ui.weather.current

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abhitom.mausamproject.R
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.databinding.CurrentWeatherFragmentBinding
import com.abhitom.mausamproject.internal.TimeConverter
import com.abhitom.mausamproject.ui.base.ScopedFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*


class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    private lateinit var binding: CurrentWeatherFragmentBinding
    override val kodein by closestKodein()
    private val viewModelFactory:CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = CurrentWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUi()

        val values = mutableListOf<Entry>()
        values.add(Entry(0f, 3f))
        values.add(Entry(1f, 0f))
        values.add(Entry(2f, 6f))
        values.add(Entry(3f, 9f))
        values.add(Entry(4f, 2f))
        values.add(Entry(5f, 8f))
        buildGraph(values)
    }

    private fun bindUi() = launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            setTempAndFeelsLike(it)
            setEveryThingExceptWind(it)
            setWindData(it)
            setWeatherImage(it)
        })
    }

    private fun setWeatherImage(currentWeather: Current) {
        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                when (currentWeather.weather?.get(0)?.id) {
                    in 200..233 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.thunderstorm_night)
                    }
                    in 300..322 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.drizzle_night)
                    }
                    in 500..532 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.rainy_night)
                    }
                    in 701..800 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.sunny_night)
                    }
                    in 801..805 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.cloud_night)
                    }
                }
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                when (currentWeather.weather?.get(0)?.id) {
                    in 200..233 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.thunderstorm_day)
                    }
                    in 300..322 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.drizzle_day)
                    }
                    in 500..532 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.rainy_day)
                    }
                    in 701..800 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.sunny_day)
                    }
                    in 801..805 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.cloud_day)
                    }
                }
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                when (currentWeather.weather?.get(0)?.id) {
                    in 200..233 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.thunderstorm_day)
                    }
                    in 300..322 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.drizzle_day)
                    }
                    in 500..532 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.rainy_day)
                    }
                    in 701..800 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.sunny_day)
                    }
                    in 801..805 -> {
                        binding.ivCurrentBackground.setImageResource(R.drawable.cloud_day)
                    }
                }
            }
        }
    }

    private fun setWindData(currentWeather: Current) {
        val windUnit:String = if(viewModel.isMetric){
            "m/s"
        }else{
            "miles/h"
        }
        val windSpeed="Wind speed\n"+currentWeather.windSpeed.toString()+" "+windUnit
        val windDirection="Wind direction\n"+currentWeather.windDeg.toString()

        binding.tvCurrentWindSpeed.text =windSpeed
        if (currentWeather.windDeg!! >340 && currentWeather.windDeg <=20){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_north_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 21..70){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_north_east_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 71..110){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_east_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 111..160){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_south_east_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 161..200){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_south_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 201..250){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_south_west_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 251..290){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_west_24, 0, 0, 0)
        }else if(currentWeather.windDeg in 291..340){
            binding.tvCurrentWindDirection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_north_west_24, 0, 0, 0)
        }
        binding.tvCurrentWindDirection.text =windDirection

    }

    private fun setEveryThingExceptWind(currentWeather: Current) {
        val humidity="Humidity\n"+currentWeather.humidity.toString() + " " + "%"
        val pressure="Pressure\n"+currentWeather.pressure.toString() + " " +"hPa"
        val uvi="UVI\n"+currentWeather.uvi.toString()
        val visibility="Visibility\n"+currentWeather.visibility.toString() + " " +"m"
        var sunrise=""
        if (currentWeather.sunrise!=null) {
            sunrise = "Sunrise\n" + TimeConverter.instance.convertToFormalTime(currentWeather.sunrise)
        }
        var sunset=""
        if (currentWeather.sunset!=null) {
            sunset = "Sunset\n" + TimeConverter.instance.convertToFormalTime(currentWeather.sunset)
        }

        binding.tvCurrentHumidity.text = humidity
        binding.tvCurrentPressure.text = pressure
        binding.tvCurrentUvi.text = uvi
        binding.tvCurrentVisibility.text = visibility
        binding.tvCurrentSunrise.text = sunrise
        binding.tvCurrentSunset.text = sunset
    }

    private fun setTempAndFeelsLike(currentWeather: Current) {
        val degree:String = if(viewModel.isMetric){
            "C"
        }else{
            "F"
        }
        val feelsLikeTemp="feels like " + currentWeather.feelsLike.toString()+" "+degree
        val temp=currentWeather.temp.toString()
        binding.tvCurrentTemp.text = temp
        binding.tvCurrentDegree.text = degree
        binding.tvCurrentFeelslike.text = feelsLikeTemp
    }

    private fun buildGraph(values: MutableList<Entry>) {

        val set = LineDataSet(values, "DataSet 1")
        set.lineWidth = 4f
        set.circleRadius = 5f
        set.circleHoleColor = Color.BLACK
        set.circleHoleRadius = 1f
        set.color=Color.BLACK
        set.setCircleColor(Color.BLACK)
        set.highLightColor = Color.BLACK
        set.setDrawValues(false)
        val data=LineData(set)

        var color=Color.WHITE
        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> color=Color.parseColor("#393A3A")
            Configuration.UI_MODE_NIGHT_NO -> color=Color.WHITE
            Configuration.UI_MODE_NIGHT_UNDEFINED -> Color.WHITE
        }

        setupChart(binding.mpchartCurrentHourly, data, color)
    }

    private fun setupChart(chart: LineChart, data: LineData, color: Int) {
        (data.getDataSetByIndex(0) as LineDataSet).circleHoleColor = color

        val xAxisValues: List<String> = listOf("12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM")
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setTouchEnabled(false)
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.setPinchZoom(false)
        chart.setBackgroundColor(color)
        chart.data = data
        chart.extraBottomOffset =10f
        chart.extraLeftOffset=20f
        chart.extraRightOffset =20f

        val l: Legend = chart.legend
        l.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisLeft.spaceTop = 40f
        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false

        val xAxis: XAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(false)
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisLineWidth=0f
        xAxis.axisLineColor=color
        xAxis.textSize=13f
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        chart.animateX(0)
    }


}