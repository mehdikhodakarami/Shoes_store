package com.example.nikestore_tir_1403.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.common.NikeFragment
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import com.example.nikestore_tir_1403.data.CartItem
import com.example.nikestore_tir_1403.feature.auth.AuthActivity
import com.example.nikestore_tir_1403.feature.main.product_detail.ProductDetailActivity
import com.example.nikestore_tir_1403.feature.shipping.ShippingActivity
import com.example.nikestore_tir_1403.services.ImageLoadingService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : NikeFragment() ,CartItemAdapter.CartItemViewCallBacks{
    val cartViewModel:CartViewModel by viewModel()
    var cartItemsAdapter:CartItemAdapter? = null
    val imageLoadingService: ImageLoadingService by inject()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart ,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartItemRv = view.findViewById<RecyclerView >(R.id.cartItemsRv)
        val payBtn = view.findViewById<Button>(R.id.payBtn)
        cartViewModel.progressLiveData.observe(this){
            setProgressIndicator(it)
        }
        payBtn.setOnClickListener {
            startActivity(Intent(requireActivity(),ShippingActivity::class.java).apply {
putExtra(EXTRA_KEY_DATA,cartViewModel.purchaseDetailLiveData.value)

            })
        }
        cartViewModel.cartItemsLiveData.observe(this){
cartItemRv.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            cartItemsAdapter = CartItemAdapter(it as MutableList<CartItem>, imageLoadingService,this )
            cartItemRv.adapter = cartItemsAdapter

        }
        cartViewModel.purchaseDetailLiveData.observe(this){
cartItemsAdapter?.let {adapter ->
    adapter.purchaseDetail.value = it
    adapter.notifyItemChanged(adapter.cartItems.size)
}
        }
        cartViewModel.emptyStateLiveData.observe(this){
            if(it.mustShow){
                val emptyState = showEmptyState(R.layout.view_empty_state_cart)

                emptyState?.let {view->
                    val emptyStateTvMessage =  view.findViewById<TextView>(R.id.empty_state_message_cart_tv)
                    val emptyStateCArtbtn = view.findViewById<Button >(R.id.emptyStateCartBtn)
                    emptyStateTvMessage.text = getString(it.messageResId)
                    emptyStateCArtbtn.visibility=if(it.mustShowCallToActionBtn == true) View.VISIBLE else View.GONE
                    emptyStateCArtbtn.setOnClickListener { startActivity(Intent(requireContext(),AuthActivity::class.java)) }
                }
            }else
                view.findViewById<View>(R.id.emptyStateRootView)?.visibility = View.GONE

        }
    }

    override fun onStart() {
        cartViewModel.refreshCart()
        super.onStart()
    }




    // callBacks from Adapter itemView-----------------------------------


    override fun onRemoveCartitembtnClick(cartItem: CartItem) {
        cartViewModel.removeItemFromCart(cartItem).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemsAdapter?.removeCartItem(cartItem)
                }

            })
    }

    override fun increaseItemBtnClick(cartItem: CartItem) {
cartViewModel.increaseCartItemCount(cartItem).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    .subscribe(object : NikeCompletableObserver(compositeDisposable){
        override fun onComplete() {
            cartItemsAdapter?.increaseCount(cartItem)
        }

    })
    }

    override fun decreaseItemBtnClick(cartItem: CartItem) {
        cartViewModel.decreaseCartItemCount(cartItem).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemsAdapter?.decreaseCount(cartItem)
                }

            })    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply { putExtra(
            EXTRA_KEY_DATA,cartItem.product) })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    //-------------------------------------------------------------------------------
}