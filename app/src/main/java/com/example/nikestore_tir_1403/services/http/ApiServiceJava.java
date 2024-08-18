package com.example.nikestore_tir_1403.services.http;

import com.example.nikestore_tir_1403.data.Banner;
import com.example.nikestore_tir_1403.data.Product;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceJava {

        @GET("product/list/")
        public Call<List<Product>> getProducts();
@GET("banner/slider/")
        public Call<List<Banner>> getBanners();

}


