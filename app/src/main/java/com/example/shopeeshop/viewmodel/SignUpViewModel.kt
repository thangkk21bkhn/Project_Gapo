package com.example.shopeeshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeeshop.model.LoginRequest
import com.example.shopeeshop.model.LoginResponse
import com.example.shopeeshop.model.SignUpRequest
import com.example.shopeeshop.model.SignUpResponse
import com.example.shopeeshop.repository.SignInRepository
import com.example.shopeeshop.repository.SignUpRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(val repository: SignUpRepository) : ViewModel() {
    val data: MutableLiveData<Response<SignUpResponse>> = MutableLiveData()
    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            data.value = repository.signUp(request)
        }
    }
}