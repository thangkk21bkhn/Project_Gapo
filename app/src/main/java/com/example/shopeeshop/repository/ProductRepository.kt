package com.example.shopeeshop.repository

import com.example.shopeeshop.api.ProductApi
import com.example.shopeeshop.api.RetrofitShopee
import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.ProductResponse
import retrofit2.Response

class ProductRepository {
    suspend fun getProduct() : Response<ListProductResponse> {
        return RetrofitShopee.productApi.getProduct()
    }
}