package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.services.http.Apiservice
import io.reactivex.Completable
import io.reactivex.Single


class ProductRemoteDataSource(val apiservice: Apiservice):ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
       return apiservice.getProducts(sort) }

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(product:Product): Completable {
        TODO("Not yet implemented")
    }

}