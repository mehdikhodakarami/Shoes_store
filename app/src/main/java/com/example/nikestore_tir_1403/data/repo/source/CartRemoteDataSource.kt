package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.AddToCartResponse
import com.example.nikestore_tir_1403.data.CartItemCount
import com.example.nikestore_tir_1403.data.CartResponse
import com.example.nikestore_tir_1403.data.MessageResponse
import com.example.nikestore_tir_1403.services.http.Apiservice
import com.google.gson.JsonObject
import io.reactivex.Single
import org.json.JSONObject

class CartRemoteDataSource(val apiservice: Apiservice) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
       return apiservice.addToCart(JsonObject().apply { addProperty("product_id",productId) })
    }

    override fun get(): Single<CartResponse> {
return apiservice.getCart()

    }

    override fun deleteItem(cartItemId: Int): Single<MessageResponse> {
       return  apiservice.removeItemFromCart(JsonObject().apply {
            addProperty("cart_item_id",cartItemId)

        })
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
return apiservice.changeCount(JsonObject().apply {
    addProperty("cart_item_id",cartItemId)
    addProperty("count",count)
})
    }

    override fun getCartItemCount(): Single<CartItemCount> {
       return  apiservice.getCartItemCount()

    }
}