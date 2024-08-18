package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.Product
import io.reactivex.Completable
import io.reactivex.Single


interface ProductDataSource {
    fun getProducts(sort:Int): Single<List<Product>>
    fun getFavoriteProducts(): Single<List<Product>>
    fun addToFavorite(product: Product): Completable
    fun deleteFromFavorite(product:Product): Completable
}