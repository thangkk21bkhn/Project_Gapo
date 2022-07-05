package com.example.shopeeshop.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitShopee {
    val baseUrl = "https://shop-ec-pro.herokuapp.com/"

    val retrofit : Retrofit by lazy {
        Retrofit.Builder().baseUrl(baseUrl).
        addConverterFactory(GsonConverterFactory.create()).build()
    }

    val productApi : ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val signInApi : SignInApi by lazy {
        retrofit.create(SignInApi::class.java)
    }

    val signUpApi : RegisterApi by lazy {
        retrofit.create(RegisterApi::class.java)
    }
}