package com.example.shopeeshop.repository

import com.example.shopeeshop.api.RetrofitShopee
import com.example.shopeeshop.model.LoginRequest
import com.example.shopeeshop.model.LoginResponse
import com.example.shopeeshop.model.SignUpRequest
import com.example.shopeeshop.model.SignUpResponse
import retrofit2.Response

class SignUpRepository {
    suspend fun signUp(request: SignUpRequest) : Response<SignUpResponse> {
        return RetrofitShopee.signUpApi.signUp(request)
    }
}