package com.example.shopeeshop.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Product::class), version = 5)
abstract class MyDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}