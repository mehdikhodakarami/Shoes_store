package com.example.nikestore_tir_1403.data

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize


@Parcelize
data class Banner(
    val id: Int,
    val image: String,
    val link_type: Int,
    val link_value: String
):Parcelable