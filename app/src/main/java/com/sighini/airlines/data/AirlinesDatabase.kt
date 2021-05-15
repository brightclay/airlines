package com.sighini.airlines.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * AirlinesDatabase provides a reference to the dao to repositories
 */
@Database(entities = [Airline::class], version = 1, exportSchema = false)
abstract class AirlinesDatabase : RoomDatabase() {

    abstract val airlinesDao: AirlinesDao

    companion object {

        @Volatile
        private var INSTANCE: AirlinesDatabase? = null

        fun getInstance(context: Context): AirlinesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AirlinesDatabase::class.java, "airlines.db")
                .build()
    }
}
