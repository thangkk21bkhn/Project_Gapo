package com.example.shopeeshop.adapter

import com.example.shopeeshop.model.ProductResponse
import com.example.shopeeshop.repository.ProductRepository

interface ClickProduct {
    fun clickItem(productResponse: ProductResponse) : Unit
}