package com.example.nikestore_tir_1403.feature.main.product_detail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.data.Comment
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.repo.CartRepo
import com.example.nikestore_tir_1403.data.repo.CommentRepo
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProductDetailViewModel(bundle: Bundle,private val commentRepo: CommentRepo,val cartRepo: CartRepo) :NikeViewModel(){
    val productLiveData  =  MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    init{
productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
commentRepo.getComments(productLiveData.value!!.id)
    .subscribeOn(Schedulers.io() ).doFinally { progressBarLiveData.postValue(false)   }.observeOn(AndroidSchedulers.mainThread()).

    subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
        override fun onSuccess(t: List<Comment>) {
commentLiveData.value = t
            Timber.i("the size "+t.size)
        }

    })
            }
    fun addToCartBtn():Completable =   cartRepo.addToCart(productLiveData.value!!.id).ignoreElement()

fun favoriteBtnClick(product: Product):Completable?{
    return null

}
}