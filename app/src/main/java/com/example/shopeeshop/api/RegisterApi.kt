package com.example.shopeeshop.api

import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.SignUpRequest
import com.example.shopeeshop.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterApi {
    @POST("api/users")
    suspend fun signUp(@Body request: SignUpRequest) : Response<SignUpResponse>
}