package com.example.shopeeshop.adapter

import com.example.shopeeshop.database.Product
import com.example.shopeeshop.model.ProductResponse

interface DeleteProduct {
    fun delete(productResponse: Product)
    fun changeData(product: Product)
}