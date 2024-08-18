package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.Banner
import io.reactivex.Single

interface BannerRepo {
    fun getBanners():Single<List<Banner>>
}