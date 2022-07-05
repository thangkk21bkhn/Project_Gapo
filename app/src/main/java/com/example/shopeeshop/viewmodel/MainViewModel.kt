package com.example.shopeeshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(val repository: ProductRepository) : ViewModel() {
    val data : MutableLiveData<Response<ListProductResponse>> = MutableLiveData()
     fun getData(){
        viewModelScope.launch {
            data.value = repository.getProduct()
        }
    }


}