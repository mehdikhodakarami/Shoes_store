package com.example.nikestore_tir_1403.data.api_with_java.repo.source

import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.services.http.ApiServiceJava
import io.reactivex.Completable
import retrofit2.Call

class ProductJavaRemoteDataSource (val apiServiceJava: ApiServiceJava):ProductJavaDataSource{
    override fun getProducts(): Call<List<Product>> {
      return  apiServiceJava.products
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