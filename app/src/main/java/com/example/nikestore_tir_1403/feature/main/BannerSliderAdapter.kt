package com.example.nikestore_tir_1403.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nikestore_tir_1403.data.Banner

class BannerSliderAdapter(fragment: Fragment,val banners:List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount() : Int= banners.size

    override fun createFragment(position: Int): Fragment {
        return BannerFragment.newInstance(banners[position])
    }
}