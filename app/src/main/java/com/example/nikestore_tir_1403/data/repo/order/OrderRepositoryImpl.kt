package com.example.nikestore_tir_1403.data.repo.order

import com.example.nikestore_tir_1403.data.Checkout
import com.example.nikestore_tir_1403.data.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl (val orderDataSource: OrderDataSource):OrderRepo{
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)
    }

    override fun checkout(orderId: Int): Single<Checkout> {
        return orderDataSource.checkout(orderId)
    }
}