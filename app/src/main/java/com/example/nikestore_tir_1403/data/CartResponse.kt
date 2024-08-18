package com.example.nikestore_tir_1403.data

import android.os.Parcelable
import com.example.nikestore_tir_1403.data.CartItem
import kotlinx.parcelize.Parcelize

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)
@Parcelize
data class PurcheseDetail(var totalPrice: Int,var shippingCost: Int,var payablePrice: Int) :
    Parcelable