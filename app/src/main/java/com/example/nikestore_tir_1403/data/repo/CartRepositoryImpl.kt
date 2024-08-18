package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.AddToCartResponse
import com.example.nikestore_tir_1403.data.CartItemCount
import com.example.nikestore_tir_1403.data.CartResponse
import com.example.nikestore_tir_1403.data.MessageResponse
import com.example.nikestore_tir_1403.data.repo.source.CartRemoteDataSource
import io.reactivex.Single

class CartRepositoryImpl(val cartRemoteDataSource: CartRemoteDataSource) : CartRepo {
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
        return cartRemoteDataSource.addToCart(productId)

    }

    override fun get(): Single<CartResponse> {
       return cartRemoteDataSource.get()
    }

    override fun deleteItem(cartItemId: Int): Single<MessageResponse> {
return cartRemoteDataSource.deleteItem(cartItemId)

    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        return cartRemoteDataSource.changeCount(cartItemId,count)
    }

    override fun getCartItemCount(): Single<CartItemCount> {
return cartRemoteDataSource.getCartItemCount()   }
}