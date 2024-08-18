package com.example.nikestore_tir_1403.feature.main.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.formatPrice
import com.example.nikestore_tir_1403.common.implementSpringAnimationTrait
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.services.ImageLoadingService
import com.example.nikestore_tir_1403.view.NikeImageView
import timber.log.Timber

const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_LARGE = 2
class ProductListAdapter(var viewType : Int = 0 ,val imageLoadingService: ImageLoadingService) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
var productClickListener : ProductEventListener? = null
    object InputFramgnet{
        var inFragmentLiveData = MutableLiveData<Boolean>()
    }

    var latestProducts = ArrayList<Product>()
    set(value) {field = value
        notifyDataSetChanged()}
    var popularestProducts = ArrayList<Product>()
        set(value) {field = value
            notifyDataSetChanged()}

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
val favoriteBtn = itemView.findViewById<ImageView>(R.id.favoriteBtn)
        val productIv = itemView.findViewById<NikeImageView>(R.id.productIv)
       val titleTv = itemView.findViewById<TextView>(R.id.ProductTitleTv)
        val previousPriceTv = itemView.findViewById<TextView>(R.id.previousPriceTv)
        val currentPriceTv =  itemView.findViewById<TextView>(R.id.currentPriceTv)


fun bindProduct(product: Product){

    if (product.isFavorite){
        favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
    }
    else
        favoriteBtn.setImageResource(R.drawable.ic_favorites)

    favoriteBtn.setOnClickListener {
    productClickListener?.favoriteBtnClick(product)
//        product.isFavorite =!product.isFavorite

        notifyItemChanged(adapterPosition)

}
    imageLoadingService.load(productIv,product.image)
    titleTv.text = product.title
    previousPriceTv.text = formatPrice(product.previous_price)
    previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    currentPriceTv.text = formatPrice(product.price)
    itemView.implementSpringAnimationTrait()
    itemView.setOnClickListener{
        productClickListener?.onProductClick(product)
    }




}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutResId  = when (viewType){
            VIEW_TYPE_ROUND -> R.layout.item_product
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            else -> throw IllegalAccessException("error view TYPE") }

return ProductViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false) )
          }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun getItemCount(): Int {
        return latestProducts.size    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(latestProducts[position])
        Timber.i("the possitioin ${position} is called")

   }
    interface ProductEventListener{
        fun onProductClick(product: Product)
        fun favoriteBtnClick(product: Product)
    }
}