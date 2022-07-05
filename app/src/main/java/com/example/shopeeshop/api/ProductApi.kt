package com.example.shopeeshop.api

import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("api/products")
    suspend fun getProduct() : Response<ListProductResponse>
}