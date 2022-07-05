package com.example.shopeeshop.api

import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.LoginRequest
import com.example.shopeeshop.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInApi {
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest) : Response<LoginResponse>
}