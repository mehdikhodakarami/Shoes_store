package com.example.nikestore_tir_1403.services.http

import android.media.session.MediaSession.Token
import com.example.nikestore_tir_1403.data.*
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Apiservice{
@GET("product/list/")
fun getProducts(@Query("sort") sort:Int): io.reactivex.Single<List<Product>>

@GET("banner/slider/")
fun getBanners():io.reactivex.Single<List<Banner>>
@GET("comment/list")
fun getComments(@Query("product_id") productId:Int):Single<List<Comment>>
@POST("cart/add")
fun addToCart(@Body jsonObject: JsonObject):Single<AddToCartResponse>
@POST("cart/remove")
fun removeItemFromCart(@Body jsonObject: JsonObject):Single<MessageResponse>
@GET("cart/list")
fun getCart():Single<CartResponse>
@POST("cart/changeCount")
fun changeCount(@Body jsonObject: JsonObject):Single<AddToCartResponse>
@GET("cart/count")
fun getCartItemCount():Single<CartItemCount>

@POST("auth/token")
fun login(@Body jsonObject: JsonObject):Single<TokenResponse>
@POST("user/register")
fun signUp(@Body jsonObject: JsonObject):Single<MessageResponse>
@POST("auth/token")
fun refreshToken(@Body jsonObject: JsonObject):Call<TokenResponse>
    @POST("order/submit")
    fun submitOrder(@Body jsonObject: JsonObject):Single<SubmitOrderResult>
    @GET("order/checkout")
    fun checkout(@Query("order_id") order_id : Int):Single<Checkout>
}

fun createApiServiceInstance ():Apiservice{
val okHttp= OkHttpClient.Builder().addInterceptor{
val oldRequest = it.request()
    val newReq = oldRequest.newBuilder()
if(TokenContainer.token != null){
    newReq.addHeader("Authorization","Bearer ${TokenContainer.token}")
}
//    newReq.addHeader("Authorization","Bearer ${TokenContainer.token}")

    newReq.addHeader("Accept","application/json")
newReq.method(oldRequest.method(),oldRequest.body())
    return@addInterceptor it.proceed(newReq.build())
}.build()


     val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl("https://fapi.7learn.com/api/v1/").client(okHttp).build()
    return retrofit.create(Apiservice::class.java)
}