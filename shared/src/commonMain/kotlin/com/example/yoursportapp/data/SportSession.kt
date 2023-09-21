package com.example.yoursportapp.data

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SportSession(
    @SerialName("id")
    val id : Int,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("name")
    val name: String,
    @SerialName("exercises")
    val exercises : MutableList<Exercise>? = null
    ){
    constructor(id: Int, date: LocalDate, name: String)
            : this(id,date,name,null) {

    }
}