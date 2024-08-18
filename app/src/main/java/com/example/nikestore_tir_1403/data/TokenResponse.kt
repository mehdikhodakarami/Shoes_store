package com.example.nikestore_tir_1403.data

data class TokenResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)