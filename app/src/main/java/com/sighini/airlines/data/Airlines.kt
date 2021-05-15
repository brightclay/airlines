package com.sighini.airlines.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class Airlines : ArrayList<Airline>()

@Entity(tableName = "airlines")
data class Airline(
    val __clazz: String,
    @PrimaryKey
    val code: String,
    val defaultName: String,
    val logoURL: String,
    val name: String,
    val phone: String,
    val site: String,
    val usName: String,
    var favorite: Boolean = false
): Serializable