package com.divyakaushik.weatherradar.network

//import com.divyakaushik.weatherradar.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository {
    private val baseUrl = "https://api.openweathermap.org"
    val id ="1277333"
    val appId = "a3e85d294de5882a1409c36769a5bd99"
    private val client = OkHttpClient().newBuilder()
            .cache(null)
            /*.addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })*/
            .build()
    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val weatherService = retrofit.create(WeatherService::class.java)
}