package com.example.nikestore_tir_1403.feature.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.services.ImageLoadingService
import com.example.nikestore_tir_1403.view.NikeImageView

class FavoriteProductsAdapter(val productList :MutableList<Product>
,val favoriteProductEventListener: FavoriteProductEventListener
,val imageViewLoadingService: ImageLoadingService):
    RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductsViewHolder>() {

    inner class FavoriteProductsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleTv = itemView.findViewById<TextView>(R.id.productTitle_favorite_Tv)
        val productiv = itemView.findViewById<NikeImageView>(R.id.productFavoriteIv)
        fun bindProduct(product: Product){
            itemView.setOnClickListener {favoriteProductEventListener.onClick(product)  }
            itemView.setOnLongClickListener{productList.remove(product)
                product.isFavorite = !product.isFavorite
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener.onLongClick(product)

            return@setOnLongClickListener false}
titleTv.text = product.title
imageViewLoadingService.load(productiv,product.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductsViewHolder {
return FavoriteProductsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_products,parent,false))
   }

    override fun getItemCount(): Int {
return productList.size    }

    override fun onBindViewHolder(holder: FavoriteProductsViewHolder, position: Int) {
        holder.bindProduct(productList.get(position))
    }
    interface FavoriteProductEventListener{
fun onClick(product: Product)
fun onLongClick(product: Product)
    }
}