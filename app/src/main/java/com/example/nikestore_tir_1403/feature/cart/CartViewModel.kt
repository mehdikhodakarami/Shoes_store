package com.example.nikestore_tir_1403.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.NikeViewModel
import com.example.nikestore_tir_1403.common.asyncNetworkRequest
import com.example.nikestore_tir_1403.common.observer.NikeSingleObserver
import com.example.nikestore_tir_1403.data.*
import com.example.nikestore_tir_1403.data.repo.CartRepo
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus

class CartViewModel(val cartRepo: CartRepo) : NikeViewModel() {
    val cartItemsLiveData= MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData=MutableLiveData<PurcheseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyStates>()
    private fun getCartItems(){
        if (!TokenContainer.token.isNullOrEmpty()){
            progressLiveData.postValue(true)
            emptyStateLiveData.value = EmptyStates(false)
            cartRepo.get().asyncNetworkRequest().doFinally { progressLiveData.postValue(false) }.subscribe(object :NikeSingleObserver<CartResponse>(compositeDisposable){
                override fun onSuccess(t: CartResponse) {
                    if(!t.cart_items.isNullOrEmpty()){
                        cartItemsLiveData.value = t.cart_items
                        purchaseDetailLiveData.value = PurcheseDetail(t.total_price,t.shipping_cost,t.payable_price)
                    }else
                        emptyStateLiveData.value = EmptyStates(true,R.string.cartEmptyState)
                }

            })
        }else
            emptyStateLiveData.value  = EmptyStates(true,R.string.cartEmptyStateLoginRequired,true)
    }
    fun removeItemFromCart(cartItem: CartItem):Completable{
        return cartRepo.deleteItem(cartItem.cart_item_id).doAfterSuccess{                    calculateAndPublicPurchaseDetail()
        cartItemsLiveData.value?.let { if(it.isEmpty()){
            emptyStateLiveData.postValue(EmptyStates(true,R.string.cartEmptyState))
            calculateAndPublicPurchaseDetail()
        }
            var cartItemCounts  = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCounts?.let { it.count-=cartItem.count
                EventBus.getDefault().postSticky(it)}

        }
        }.ignoreElement()
    }
    fun increaseCartItemCount(cartItem: CartItem):Completable {


        return cartRepo.changeCount(cartItem.cart_item_id,++ cartItem.count).doAfterSuccess{
            calculateAndPublicPurchaseDetail()
            var cartItemCounts  = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCounts?.let { it.count+=1
            EventBus.getDefault().postSticky(it)}
        }.ignoreElement()}
    fun decreaseCartItemCount(cartItem: CartItem):Completable {
        return cartRepo.changeCount(cartItem.cart_item_id,-- cartItem.count).doAfterSuccess{
            calculateAndPublicPurchaseDetail()
            var cartItemCounts  = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCounts?.let { it.count-=1
                EventBus.getDefault().postSticky(it)}
        }.ignoreElement()}
    fun refreshCart(){
        getCartItems()
    }
    fun calculateAndPublicPurchaseDetail(){
cartItemsLiveData.value?.let {cartItems ->

    purchaseDetailLiveData.value?.let { purcheseDetail ->
        var totalPrice = 0
        var payablePrice = 0
        cartItems.forEach {
            totalPrice += it.product.price * it.count
            payablePrice += (it.product.price - it.product.discount) * it.count
        }
        purcheseDetail.totalPrice= totalPrice
        purcheseDetail.payablePrice = payablePrice


    }
    
}

    }

}