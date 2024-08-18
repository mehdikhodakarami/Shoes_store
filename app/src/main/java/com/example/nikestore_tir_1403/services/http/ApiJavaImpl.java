package com.example.nikestore_tir_1403.services.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiJavaImpl {
    public static ApiServiceJava getInsApiJava(){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                baseUrl("http://expertdevelopers.ir/api/v1/").build();
        return retrofit.create(ApiServiceJava.class);
    }
}
