package com.example.yoursportapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserToken(
    @SerialName("access_token")
    val token: String?,
    @SerialName("token_type")
    val token_type: String?,

    )