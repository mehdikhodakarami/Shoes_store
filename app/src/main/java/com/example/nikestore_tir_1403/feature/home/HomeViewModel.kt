package com.example.nikestore_tir_1403.feature.home

import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import com.example.nikestore_tir_1403.data.Banner
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.SORT_LATEST
import com.example.nikestore_tir_1403.data.SORT_POPULER
import com.example.nikestore_tir_1403.data.api_with_java.repo.BannerJavaRepo
import com.example.nikestore_tir_1403.data.api_with_java.repo.ProductJavaRepo
import com.example.nikestore_tir_1403.data.repo.BannerRepo
import com.example.nikestore_tir_1403.data.repo.ProductRepo
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

import timber.log.Timber

class HomeViewModel(val productRepo: ProductRepo, val productJavaRepo: ProductJavaRepo,
                    val bannerRepo: BannerRepo, val bannerJavaRepo: BannerJavaRepo): NikeViewModel() {
    val latestProductLiveData = MutableLiveData<List<Product>>()
    val popularisesProductLiveData = MutableLiveData<List<Product>>()
    var progressBarLiveData = MutableLiveData<Boolean>()
    var bannerLiveData = MutableLiveData<List<Banner>>()
    var testData = 18


    init {
        progressBarLiveData.value = true

        bannerRepo.getBanners().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {

                    bannerLiveData.value = t

                }
            })
        //product request from Single----------------------------------------------------------------------
       productRepo.getProducts(SORT_LATEST).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread() )
           .doFinally { progressBarLiveData.value = false }
           .subscribe(object: NikeSingleObserver<List<Product>>(compositeDisposable){
            override fun onSuccess(t: List<Product>) { latestProductLiveData.value = t
            Timber.i("the rx java product is ${t.size}")}}      );


        productRepo.getProducts(SORT_POPULER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }.subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    popularisesProductLiveData.value = t
                }

            })

           }


fun addProductToFavorites(product: Product){
    if(product.isFavorite){
        productRepo.deleteFromFavorite(product).subscribeOn(Schedulers.io()).subscribe(object : NikeCompletableObserver(compositeDisposable){
            override fun onComplete(){
                product.isFavorite = false
            }

        })
    }else {
        productRepo.addToFavorite(product).subscribeOn(Schedulers.io()).subscribe(object : NikeCompletableObserver(compositeDisposable){
            override fun onComplete(){
                product.isFavorite = true
            }

        })
    }

}
    fun isFavorite(product: Product):Boolean{
        var bool = false
        productRepo.getFavoriteProducts().observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                   t.forEach { if (it.id == product.id){
                   bool=true}
                   }




                }

            })
        return bool
    }
    }

