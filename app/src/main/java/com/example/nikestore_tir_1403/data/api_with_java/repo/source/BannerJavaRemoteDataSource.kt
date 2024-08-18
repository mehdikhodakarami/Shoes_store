package com.example.nikestore_tir_1403.data.api_with_java.repo.source

import com.example.nikestore_tir_1403.data.Banner
import com.example.nikestore_tir_1403.services.http.ApiServiceJava
import retrofit2.Call

class BannerJavaRemoteDataSource(val apiServiceJava: ApiServiceJava) : BannerJavaDataSource{
    override fun getBanners(): Call<List<Banner>> {
        return apiServiceJava.banners
    }
}