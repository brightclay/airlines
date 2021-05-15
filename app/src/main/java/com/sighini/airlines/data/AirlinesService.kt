package com.sighini.airlines.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val service: AirlinesService by lazy {

     val retrofit = Retrofit.Builder()
            .baseUrl(AirlinesRepository.BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

     retrofit.create(AirlinesService::class.java)
}

fun getNetworkService() = service

interface AirlinesService {

    @GET("h/mobileapis/directory/airlines")
    suspend fun getAirlines() : Airlines

}