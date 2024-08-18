package com.example.nikestore_tir_1403.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddToCartResponse (
    val id : Int,
    val product_id : Int,
    val count : Int
        ) : Parcelable