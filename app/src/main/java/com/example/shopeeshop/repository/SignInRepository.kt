package com.example.shopeeshop.repository

import com.example.shopeeshop.api.RetrofitShopee
import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.LoginRequest
import com.example.shopeeshop.model.LoginResponse
import retrofit2.Response

class SignInRepository {
    suspend fun login(request: LoginRequest) : Response<LoginResponse> {
        return RetrofitShopee.signInApi.login(request)
    }
}