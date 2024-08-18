package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.repo.source.ProductDataSource
import com.example.nikestore_tir_1403.data.repo.source.ProductLocalDataSource
import com.example.nikestore_tir_1403.data.repo.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single


class ProductRepositoryImpl(val remoteDataSource: ProductDataSource
,val localDataSource: ProductLocalDataSource):ProductRepo {
    override fun getProducts(sort:Int): Single<List<Product>> {
return localDataSource.getFavoriteProducts().flatMap { favorites->remoteDataSource.getProducts(sort).doOnSuccess {
    val favoriteProducIds = favorites.map { favoritesPro-> favoritesPro.id }
    it.forEach { product -> if(favoriteProducIds.contains(product.id)){
        product.isFavorite = true
    }


    }

} }
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
return localDataSource.getFavoriteProducts()    }

    override fun addToFavorite(product: Product): Completable {
return localDataSource.addToFavorite(product)   }

    override fun deleteFromFavorite(product:Product): Completable {
        return localDataSource.deleteFromFavorite(product)
    }
}