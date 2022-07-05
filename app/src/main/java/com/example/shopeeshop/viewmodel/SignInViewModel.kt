package com.example.shopeeshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.LoginRequest
import com.example.shopeeshop.model.LoginResponse
import com.example.shopeeshop.repository.ProductRepository
import com.example.shopeeshop.repository.SignInRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignInViewModel(val repository: SignInRepository) : ViewModel() {
    val data: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            data.value = repository.login(request)
        }
    }
}