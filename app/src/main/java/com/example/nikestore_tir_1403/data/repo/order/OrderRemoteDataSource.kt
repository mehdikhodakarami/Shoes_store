package com.example.nikestore_tir_1403.data.repo.order

import com.example.nikestore_tir_1403.data.Checkout
import com.example.nikestore_tir_1403.data.SubmitOrderResult
import com.example.nikestore_tir_1403.services.http.Apiservice
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource (val apiservice: Apiservice):OrderDataSource{
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {


        return apiservice.submitOrder(JsonObject().apply {
            addProperty("first_name",firstName)
            addProperty("last_name",lastName)
            addProperty("postal_code",postalCode)
            addProperty("mobile",phoneNumber)
            addProperty("address",address)
            addProperty("payment_method",paymentMethod)})}

    override fun checkout(orderId: Int): Single<Checkout> {

return apiservice.checkout(orderId)
      }
}