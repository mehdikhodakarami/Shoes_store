package com.example.nikestore_tir_1403.feature.shipping

import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.data.SubmitOrderResult
import com.example.nikestore_tir_1403.data.repo.order.OrderRepo
import io.reactivex.Single
const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"
class ShippingViewModel(private val orderRepo: OrderRepo) :NikeViewModel(){
    fun submitOrder(
        firstName:String,
    lastName:String,
        postalCode:String,
        phoneNumber:String,
        address:String,
        paymentMethod:String
    ): Single<SubmitOrderResult> {
        return  orderRepo.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)
    }

}