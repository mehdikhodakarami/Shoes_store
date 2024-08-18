package com.example.nikestore_tir_1403.data.repo

import com.example.nikestore_tir_1403.data.Banner
import com.example.nikestore_tir_1403.data.repo.source.BannerDataSource

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerDataSource):BannerRepo {
    override fun getBanners(): io.reactivex.Single<List<Banner>> = bannerRemoteDataSource.getBanners("1".toInt())
}