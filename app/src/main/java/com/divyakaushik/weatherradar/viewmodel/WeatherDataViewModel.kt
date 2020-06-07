package com.divyakaushik.weatherradar.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.divyakaushik.weatherradar.model.WeatherData
import com.divyakaushik.weatherradar.model.WeatherDataProcessor
import com.divyakaushik.weatherradar.utils.DBHelper

class WeatherDataViewModel @JvmOverloads constructor(app: Application, val weatherDataProcessor: WeatherDataProcessor = WeatherDataProcessor()) : ObservableViewModel(app), WeatherDataProcessor.OnWeatherDataListener {
    var lastWeatherData = WeatherData()
    var weatherDataListener: WeatherDataProcessor.OnWeatherDataListener? = null
    val db by lazy { DBHelper(app) }

    init {
        weatherDataListener = this
        weatherDataProcessor.getWeatherData(this)
    }

    override fun onSuccess(weatherData: WeatherData) {
        weatherDataProcessor.saveWeatherData(weatherData)
        updateOutputs(weatherData)
        db.insertData(weatherData)
    }

    fun updateOutputs(wd: WeatherData) {
        lastWeatherData = wd
        notifyChange()
    }

    fun loadSavedWeatherDataSummaries(): LiveData<List<WeatherDataSummaryItem>> {
        weatherDataProcessor.mergeLocalDataList(db.readData())
        return Transformations.map(weatherDataProcessor.loadWeatherData()) { weatherDataObjects ->
            weatherDataObjects.map {
                WeatherDataSummaryItem(it.temp,
                        it.dateCreated)
            }
        }
    }
}