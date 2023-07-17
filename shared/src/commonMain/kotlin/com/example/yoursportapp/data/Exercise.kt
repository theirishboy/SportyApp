package com.example.yoursportapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    @SerialName("id")
    val id : Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description : String?,
    @SerialName("muscular_groups")
    val muscular_groups : MutableList<Exercise>,
    @SerialName("sports_sessions")
    val sport_sessions : MutableList<SportSession>)
