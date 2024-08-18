package com.example.nikestore_tir_1403.data.api_with_java.repo

import com.example.nikestore_tir_1403.data.Banner
import com.example.nikestore_tir_1403.data.api_with_java.repo.source.BannerJavaDataSource
import com.example.nikestore_tir_1403.data.repo.source.BannerRemoteDataSource
import retrofit2.Call

class BannerJavaRepoImpl (val bannerRemoteDataSource: BannerJavaDataSource):BannerJavaRepo{
    override fun getBanners(): Call<List<Banner>> {
return bannerRemoteDataSource.getBanners()}
}