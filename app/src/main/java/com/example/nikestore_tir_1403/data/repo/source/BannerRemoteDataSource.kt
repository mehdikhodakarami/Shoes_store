package com.example.nikestore_tir_1403.data.repo.source

import com.example.nikestore_tir_1403.data.Banner
import com.example.nikestore_tir_1403.services.http.Apiservice


class BannerRemoteDataSource(val apiservice: Apiservice):BannerDataSource {

    override fun getBanners(sort: Int): io.reactivex.Single<List<Banner>> {
        return apiservice.getBanners()
    }


}