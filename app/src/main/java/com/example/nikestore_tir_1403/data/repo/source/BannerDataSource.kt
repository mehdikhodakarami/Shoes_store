package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(sort:Int) : Single<List<Banner>>
}