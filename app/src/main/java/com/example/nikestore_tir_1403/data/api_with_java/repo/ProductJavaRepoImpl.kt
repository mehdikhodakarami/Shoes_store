package com.example.nikestore_tir_1403.data.api_with_java.repo

import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.ProductJavaDataSource
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.ProductJavaLocalDataSource
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.ProductJavaRemoteDataSource
import com.example.nikestore_tir_1403.services.http.ApiJavaImpl
import com.example.nikestore_tir_1403.services.http.ApiServiceJava
import io.reactivex.Completable
import retrofit2.Call

class ProductJavaRepoImpl(val remoteDataSource: ProductJavaDataSource,
val localDataSource: ProductJavaLocalDataSource) : ProductJavaRepo {
    override fun getProducts(): Call<List<Product>> {
return remoteDataSource.getProducts()
    }

    override fun getFavoriteProducts(): Call<List<Product>> {
        TODO("Not yet implemented")

    }

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }

}