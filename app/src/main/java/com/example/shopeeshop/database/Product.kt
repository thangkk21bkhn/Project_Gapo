package com.example.shopeeshop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nameProduct") val nameProduct: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "image") val img: String,
    @ColumnInfo(name = "quality") var quality: Int

) {
}
