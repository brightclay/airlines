package com.sighini.airlines.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AirlinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(airlines: List<Airline?>?)

    @Query("SELECT * FROM airlines ORDER BY usName")
    fun getAllAirlines(): PagingSource<Int, Airline>

    @Query("UPDATE airlines SET favorite = :favorite WHERE code = :code")
    suspend fun updateFavorite(code: String, favorite: Boolean?): Int
}
