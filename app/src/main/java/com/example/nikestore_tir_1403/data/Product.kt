package com.example.nikestore_tir_1403.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "products")
@Parcelize
data class Product(
    val discount: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String
) : Parcelable{
    var isFavorite :Boolean = false
}

const val SORT_LATEST=0
const val SORT_POPULER=1
const val PRICE_DESC=2
const val PRICE_ASC=3