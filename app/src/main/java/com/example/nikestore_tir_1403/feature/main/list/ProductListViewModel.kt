package com.example.nikestore_tir_1403.feature.main.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.repo.ProductRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(val productRepo: ProductRepo, var sort: Int) : NikeViewModel() {
   val productLiveData = MutableLiveData<List<Product>>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val selectedSortLiveData =  MutableLiveData<Int>()
var sortTitles = arrayOf(R.string.sortLatest,R.string.sortPopular,R.string.sortPriceHighToLow,R.string.sortPriceLowToHigh)
    init {
getProducts()
        selectedSortLiveData.value = sortTitles[sort]
    }


    fun getProducts(){
        progressBarLiveData.postValue(true)
productRepo.getProducts(sort).doAfterSuccess { progressBarLiveData.postValue(false)  }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
        override fun onSuccess(t: List<Product>) {

productLiveData.value = t } })}




fun onSelectedSourceChangedByUser(sort:Int) {
    this.sort = sort
    this.selectedSortLiveData.value = sortTitles[sort]
    getProducts()
}
    fun favoriteProductClick(product: Product){
        if(product.isFavorite)
            productRepo.deleteFromFavorite(product).subscribeOn(Schedulers.io()).
                subscribe(
                object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = false
                    }

                }
                    )
        else
            productRepo.addToFavorite(product).subscribeOn(Schedulers.io()).subscribe(
                object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = true
                    }

                }
            )
}}