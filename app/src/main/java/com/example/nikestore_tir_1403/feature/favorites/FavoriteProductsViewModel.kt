package com.example.nikestore_tir_1403.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.repo.ProductRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteProductsViewModel(private val productRepo: ProductRepo):NikeViewModel() {
    val favoriteProductsLiveData = MutableLiveData<List<Product>>()
    init {
        productRepo.getFavoriteProducts().subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    favoriteProductsLiveData.postValue(t)
                }

            })
    }
fun removeFromFavorites(product: Product){
    productRepo.deleteFromFavorite(product).subscribeOn(Schedulers.io())
        .subscribe(object : NikeCompletableObserver(compositeDisposable){
            override fun onComplete() {
product.isFavorite = !product.isFavorite
            }

        })
}
}