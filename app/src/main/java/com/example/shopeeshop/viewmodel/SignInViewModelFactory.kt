package com.example.shopeeshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopeeshop.repository.ProductRepository
import com.example.shopeeshop.repository.SignInRepository

class SignInViewModelFactory constructor(private val repository: SignInRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(repository) as T
    }
}