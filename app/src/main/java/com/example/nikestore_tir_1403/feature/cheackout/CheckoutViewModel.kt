package com.example.nikestore_tir_1403.feature.cheackout

import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.common.asyncNetworkRequest
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.data.Checkout
import com.example.nikestore_tir_1403.data.repo.order.OrderRepo

class CheckoutViewModel(orderRepo: OrderRepo,orderId:Int) :NikeViewModel() {
val checkoutLiveData = MutableLiveData<Checkout>()
    init {
        orderRepo.checkout(orderId).asyncNetworkRequest().subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable){
            override fun onSuccess(t: Checkout) {
                checkoutLiveData.value = t
            }

        })
    }

}

