package com.example.nikestore_tir_1403.data.api_with_java.repo.source

import com.example.nikestore_tir_1403.data.Product
import io.reactivex.Completable
import retrofit2.Call

interface ProductJavaDataSource {
    fun getProducts(): Call<List<Product>>
    fun getFavoriteProducts(): Call<List<Product>>
    fun addToFavorite(): Completable
    fun deleteFromFavorite(): Completable

}