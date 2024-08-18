package com.example.nikestore_tir_1403.feature.main

import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.data.CartItemCount
import com.example.nikestore_tir_1403.data.TokenContainer
import com.example.nikestore_tir_1403.data.repo.CartRepo
import com.example.nikestore_tir_1403.data.repo.CartRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(val cartREpo: CartRepo) :NikeViewModel() {
fun getCartItemCounts(){
    if(!TokenContainer.token.isNullOrEmpty()){
        cartREpo.getCartItemCount().subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<CartItemCount>(compositeDisposable){
                override fun onSuccess(t: CartItemCount) {
                    EventBus.getDefault().postSticky(t)
                }

            })
    }
}
}