package com.example.shopeeshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopeeshop.repository.SignInRepository
import com.example.shopeeshop.repository.SignUpRepository

class SignUpViewModelFactory constructor(private val repository: SignUpRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}