package com.example.nikestore_tir_1403.data.repo.source

import androidx.room.*
import com.example.nikestore_tir_1403.data.Product
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface ProductLocalDataSource:ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }
@Query("SELECT * FROM products")
    override fun getFavoriteProducts(): Single<List<Product>> 
@Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorite(product: Product): Completable
@Delete
    override fun deleteFromFavorite(product:Product): Completable

}