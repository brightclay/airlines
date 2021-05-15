package com.sighini.airlines.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AirlinesRepository private constructor(
        private val airlinesDao: AirlinesDao,
        private val airlinesService: AirlinesService,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun fetchAirlines() {
        try {
            var airlines = airlinesService.getAirlines()
            airlinesDao.insertAll(airlines)
            Log.d("TAG", "Airlines count=${airlines.size}");
        } catch (error: Exception) {
            Log.d("TAG", "Error =${error.message}");
        }
    }

    fun getAllAirlines() = airlinesDao.getAllAirlines()

    suspend fun updateFavorite(code: String, favorite: Boolean?) {
        airlinesDao.updateFavorite(code, favorite)
    }

    companion object {

        const val BASE_URL = "https://www.kayak.com/"

        // For Singleton instantiationx
        @Volatile private var instance: AirlinesRepository? = null

        fun getInstance(airlinesDao: AirlinesDao, airlinesService: AirlinesService) =
                instance ?: synchronized(this) {
                    instance ?: AirlinesRepository(airlinesDao, airlinesService).also { instance = it }
                }
    }
}