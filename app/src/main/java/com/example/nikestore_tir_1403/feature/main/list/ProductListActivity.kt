package com.example.nikestore_tir_1403.feature.main.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.common.NikeActivity
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.feature.main.product_detail.ProductDetailActivity
import com.example.nikestore_tir_1403.view.NikeToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity :NikeActivity() ,ProductListAdapter.ProductEventListener{
    lateinit var productRv : RecyclerView
    lateinit var viewChangerGrid:ImageView
    lateinit var viewSortChanger:View
    lateinit var sortSelectedTitleTv : TextView
    val productListViewModel by viewModel<ProductListViewModel> { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_DATA)) }
    val productListAdapter :ProductListAdapter by inject{ parametersOf(VIEW_TYPE_SMALL) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)


        productRv = findViewById(R.id.productsListRv)
        productListAdapter.productClickListener = this
        productRv.adapter = productListAdapter
        viewChangerGrid = findViewById<ImageView>(R.id.viewTypeChangerBtn)
        viewSortChanger = findViewById<View>(R.id.viewSortChanger)
        sortSelectedTitleTv =  findViewById<TextView>(R.id.sortSelectedTitleTv)


        var gridLayoutManager= GridLayoutManager(this,2)


        productRv.layoutManager = gridLayoutManager
productListViewModel.productLiveData.observe(this){
productListAdapter.latestProducts = it as ArrayList<Product>
}

        viewChangerGrid.setOnClickListener{

            if(productListAdapter.viewType == VIEW_TYPE_SMALL) {
                viewChangerGrid.setImageResource(R.drawable.baseline_crop_square_24)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()

            }else{
                viewChangerGrid.setImageResource(R.drawable.baseline_grid_view_24)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
                gridLayoutManager.spanCount = 2
            }

        }
        productListViewModel.selectedSortLiveData.observe(this){
           sortSelectedTitleTv.text = getString(it)
        }

findViewById<NikeToolbar>(R.id.productListToolbar).onBackClickListener = View.OnClickListener { finish() }
        viewSortChanger.setOnClickListener {

            val dialog = MaterialAlertDialogBuilder(this).setSingleChoiceItems(R.array.sortTitleArray
            ,productListViewModel.sort
            ) { dialog, sortIndex ->
                dialog.dismiss()
                productListViewModel.onSelectedSourceChangedByUser(sortIndex)


            }
            dialog.setTitle(R.string.sort)
            dialog.show()
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(
            Intent(this, ProductDetailActivity::class.java)
            .apply { putExtra(EXTRA_KEY_DATA,product) })
    }

    override fun favoriteBtnClick(product: Product) {
        productListViewModel.favoriteProductClick(product)
    }

    override fun onResume() {
        super.onResume()
        productListViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }
        ProductListAdapter.InputFramgnet.inFragmentLiveData.value =false
    }

}