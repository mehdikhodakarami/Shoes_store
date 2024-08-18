package com.example.nikestore_tir_1403.data.api_with_java.repo

import com.example.nikestore_tir_1403.data.Banner
import retrofit2.Call

interface BannerJavaRepo {
  fun  getBanners(): Call<List<Banner>>
}