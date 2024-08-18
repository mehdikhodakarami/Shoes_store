package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.Product
import io.reactivex.Completable
import retrofit2.http.Query


interface ProductRepo{
    fun getProducts(sort:Int): io.reactivex.Single<List<Product>>
    fun getFavoriteProducts():io.reactivex.Single<List<Product>>
    fun addToFavorite(product:Product): Completable
    fun deleteFromFavorite(product:Product):Completable
}