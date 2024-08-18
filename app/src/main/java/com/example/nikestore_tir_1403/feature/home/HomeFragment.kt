package com.example.nikestore_tir_1403.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.EXTRA_KEY_DATA
import com.example.nikestore_tir_1403.common.NikeFragment
import com.example.nikestore_tir_1403.common.convertDpToPixel
import com.example.nikestore_tir_1403.data.Product
import com.example.nikestore_tir_1403.data.SORT_LATEST
import com.example.nikestore_tir_1403.data.SORT_POPULER
import com.example.nikestore_tir_1403.feature.main.BannerSliderAdapter
import com.example.nikestore_tir_1403.feature.main.list.ProductListActivity
import com.example.nikestore_tir_1403.feature.main.list.ProductListAdapter
import com.example.nikestore_tir_1403.feature.main.list.VIEW_TYPE_ROUND
import com.example.nikestore_tir_1403.feature.main.product_detail.ProductDetailActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment: NikeFragment() , ProductListAdapter.ProductEventListener {
    private val homeViewModel : HomeViewModel by viewModel<HomeViewModel>()
    val productListAdapter : ProductListAdapter by inject{(parametersOf(VIEW_TYPE_ROUND))}
    val popularProductListAdapter : ProductListAdapter by inject{(parametersOf(VIEW_TYPE_ROUND))}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(com.example.nikestore_tir_1403.R.layout.fragment_main
        ,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
        //insert recyclerView
        val latestProductRv = view.findViewById<RecyclerView>(R.id.latest_product_rv)
        val popularProductsRv = view.findViewById<RecyclerView>(R.id.populerest_product_rv)
        latestProductRv.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        popularProductsRv.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)

        popularProductListAdapter.productClickListener = this@HomeFragment
        productListAdapter.productClickListener = this@HomeFragment
        latestProductRv.adapter = productListAdapter
        popularProductsRv.adapter = popularProductListAdapter


//-----------------------------------------------------------------------
view.findViewById<Button>(R.id.viewLatestProducts).setOnClickListener{
    startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {putExtra(
        EXTRA_KEY_DATA, SORT_LATEST)})
}
        view.findViewById<Button>(R.id.viewPopulerestProducts).setOnClickListener{
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {putExtra(
                EXTRA_KEY_DATA, SORT_POPULER)})
        }

        //Banner and Indicator

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner){
            val bannerSliderAdapter = BannerSliderAdapter(this,it)
            val bannerSliderViewPager : ViewPager2 = getView()!!.findViewById(R.id.banner_slider_view_pager)
            bannerSliderViewPager.setViewTreeSavedStateRegistryOwner(this)
            bannerSliderViewPager.isSaveEnabled = true
            bannerSliderViewPager.adapter = bannerSliderAdapter
//            (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
//                            32f,
//                            requireContext()
//                        )) * 173) / 328).toInt()
            val viewPagerHeight =  (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328).toInt()


            val layoutParams = bannerSliderViewPager.layoutParams
//            layoutParams.height = viewPagerHeight.toInt()
            bannerSliderViewPager.layoutParams = layoutParams
            var sliderIndicator = view.findViewById<DotsIndicator>(R.id.sliderIndicator)
            sliderIndicator.setViewPager2(bannerSliderViewPager)
        }

        //to recyclerView
            homeViewModel.latestProductLiveData.observe(viewLifecycleOwner){
                productListAdapter.latestProducts = it as ArrayList<Product>
            }
        homeViewModel.popularisesProductLiveData.observe(this ){
            popularProductListAdapter.latestProducts = it as ArrayList<Product>
        }
//-----livedata In FRagment
        ProductListAdapter.InputFramgnet.inFragmentLiveData.observe(this){

        }


            //slider





            //set current width and height




  }

    override fun onProductClick(product: Product) {
//        product.isFavorite = homeViewModel.isFavorite(product)
startActivity(Intent(requireContext(), ProductDetailActivity::class.java)
    .apply { putExtra(EXTRA_KEY_DATA,product) })
    }

    override fun favoriteBtnClick(product: Product) {
        homeViewModel.addProductToFavorites(product)
    }

    override fun onResume() {
        super.onResume()
        ProductListAdapter.InputFramgnet.inFragmentLiveData.value = true

    }

}
