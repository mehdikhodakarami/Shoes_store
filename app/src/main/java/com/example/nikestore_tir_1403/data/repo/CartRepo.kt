package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.AddToCartResponse
import com.example.nikestore_tir_1403.data.CartItemCount
import com.example.nikestore_tir_1403.data.CartResponse
import com.example.nikestore_tir_1403.data.MessageResponse
import io.reactivex.Single

interface CartRepo {
    fun addToCart(productId:Int):Single<AddToCartResponse>
    fun get():Single<CartResponse>
    fun deleteItem(cartItemId:Int):Single<MessageResponse>
    fun changeCount(cartItemId: Int,count:Int):Single<AddToCartResponse>
    fun getCartItemCount():Single<CartItemCount>
}