package com.example.shopeeshop.database

import androidx.room.*
import com.example.shopeeshop.model.ProductResponse

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product WHERE name = :nameCustomer")
    fun getByName(nameCustomer : String): List<Product>

    @Query("SELECT * FROM Product ")
    fun getAll(): List<Product>

    @Insert
    fun insertAll(vararg users: Product)

    @Delete
    fun delete(product : Product)

    @Update
    fun updateTodo(vararg product : Product)
}