package com.example.yoursportapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String,
    @SerialName("is_active")
    val is_active: String,
    @SerialName("is_admin")
    val is_admin: String,
    val sport_sessions: MutableList<SportSession>?


) {
    constructor(email: String, password: String, firstname: String, lastname: String)
            : this(0,email,password,firstname,lastname,"true","false",null) {

    }
}


