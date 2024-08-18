package com.example.nikestore_tir_1403.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.formatPrice
import com.example.nikestore_tir_1403.data.CartItem
import com.example.nikestore_tir_1403.data.PurcheseDetail
import com.example.nikestore_tir_1403.services.ImageLoadingService
import com.example.nikestore_tir_1403.view.NikeImageView

const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS =1
class CartItemAdapter(val cartItems : MutableList<CartItem>,
                      val imageLoadingService: ImageLoadingService,
                      val cartItemCallBacks: CartItemViewCallBacks) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var purchaseDetail = MutableLiveData<PurcheseDetail>()
   inner class CartItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
       val productTitleTv = itemView.findViewById<TextView>(R.id.productTitleTvCart)
       val cartItemCountTv = itemView.findViewById<TextView>(R.id.countTvCartNumber)
       val previousPriceTv = itemView.findViewById<TextView>(R.id.previousPriceCartTv)
       val priceTv = itemView.findViewById<TextView>(R.id.priceTvCart)
       val productIV = itemView.findViewById<NikeImageView >(R.id.productIvCart)
       val removeBtn = itemView.findViewById<View>(R.id.removeFromCartBtn)
       val increaseBtn = itemView.findViewById<View>(R.id.increaseBtn)
       val decreaseBtn = itemView.findViewById<View>(R.id.decreaseBtn)
       val progressBar = itemView.findViewById<ProgressBar>(R.id.changeCountProgressBar);

fun bindCartItem(cartItem: CartItem){
imageLoadingService.load(productIV,cartItem.product.image)
productTitleTv.text = cartItem.product.title
    cartItemCountTv.text = cartItem.count.toString()
    previousPriceTv.text = formatPrice(cartItem.product.previous_price+cartItem.product.discount)
    priceTv.text= formatPrice(cartItem.product.price)
    removeBtn.setOnClickListener { cartItemCallBacks.onRemoveCartitembtnClick(cartItem) }
    progressBar.visibility = if (cartItem.changeCountProgressBarIsVisible )View.VISIBLE else View.GONE
    cartItemCountTv.visibility = if (cartItem.changeCountProgressBarIsVisible )View.INVISIBLE else View.VISIBLE
    increaseBtn.setOnClickListener {
        cartItem.changeCountProgressBarIsVisible = true
        progressBar.visibility = View.VISIBLE
        cartItemCountTv.visibility = View.INVISIBLE
        cartItemCallBacks.increaseItemBtnClick(cartItem)
    }
    decreaseBtn.setOnClickListener {
        if(cartItem.count>1){
            cartItem.changeCountProgressBarIsVisible = true
            progressBar.visibility = View.VISIBLE
            cartItemCountTv.visibility = View.INVISIBLE
            cartItemCallBacks.decreaseItemBtnClick(cartItem)
        }
        }
    productIV.setOnClickListener{cartItemCallBacks.onProductImageClick(cartItem)}
    if(cartItem.changeCountProgressBarIsVisible){
        progressBar.visibility = View.VISIBLE
        increaseBtn.visibility = View.GONE
        decreaseBtn.visibility = View.GONE
    }

}

   }
     class PurchaseDetailViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val totalPriceTv = itemView.findViewById<TextView>(R.id.totalpriceNumberTv)
        val shippingCostTv = itemView.findViewById<TextView>(R.id.shippingCostNumberTv)
        val payablePriceTv = itemView.findViewById<TextView>(R.id.payableNumberTv)

        fun bindPurchase (totalPrice : Int,shippingCost:Int,payablePrice:Int){
            totalPriceTv.text = formatPrice(totalPrice)
            shippingCostTv.text = formatPrice(shippingCost)
            payablePriceTv.text = formatPrice(payablePrice)
        }
    }
    interface CartItemViewCallBacks{
        fun onRemoveCartitembtnClick(cartItem: CartItem);
        fun increaseItemBtnClick(cartItem: CartItem)
        fun decreaseItemBtnClick(cartItem: CartItem)
        fun onProductImageClick(cartItem: CartItem)

    }





    override fun getItemCount(): Int {
return cartItems.size+1
    }





    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
if (holder is CartItemViewHolder){
    holder.bindCartItem(cartItems[position])
}
else if (holder is PurchaseDetailViewHolder){
    purchaseDetail?.let {
        holder.bindPurchase(it.value!!.totalPrice,it.value!!.shippingCost,it.value!!.payablePrice)
    }
}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
if(viewType == VIEW_TYPE_ITEM){
    return CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false))
}else
    return PurchaseDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_detail,parent,false))

    }
    fun removeCartItem(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems.removeAt(index)
            notifyItemRemoved(index)
            notifyItemChanged(cartItems.size)
        }

    }
    fun increaseCount(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)

        }
    }
    fun decreaseCount(cartItem: CartItem){
        val index = cartItems.indexOf(cartItem)
        if (index > -1){
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)

        }
        }

    override fun getItemViewType(position: Int): Int {
        if(position == cartItems.size)
            return VIEW_TYPE_PURCHASE_DETAILS
        else
            return VIEW_TYPE_ITEM
    }
    }


