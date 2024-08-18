package com.example.nikestore_tir_1403.feature.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.feature.main.product_detail.ProductDetailActivity
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteProductsActivity : NikeActivity(),FavoriteProductsAdapter.FavoriteProductEventListener {
val productFavoriteProductsViewModel: FavoriteProductsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_products)
        val favoriteProductsRv = findViewById<RecyclerView >(R.id.favoriteProductsRv)
        productFavoriteProductsViewModel.favoriteProductsLiveData.observe(this){
            if (it.isNotEmpty()){
                val layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                favoriteProductsRv.layoutManager = layoutManager
                favoriteProductsRv.adapter = FavoriteProductsAdapter(it as MutableList<Product>
                    ,this,get()
                )
            }else{
                showEmptyState(R.layout.empty_state_default)
                findViewById<TextView>(R.id.empty_state_default_message_tv).text= "هنوز محصولی به لیست علاقه مندی هات اضافه نکردی "
            }

        }

    }



    override fun onClick(product: Product) {
        startActivity(Intent(this@FavoriteProductsActivity,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onLongClick(product: Product) {
        productFavoriteProductsViewModel.removeFromFavorites(product)
    }
}