package com.example.yoursportapp.data

import kotlinx.serialization.Serializable

@Serializable()
data class ApiError(
    val details: String
)
