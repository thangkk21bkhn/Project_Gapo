package com.example.shopeeshop.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("rating")
    val rating : Int,
    @SerializedName("numReviews")
    val numReviews : Int,
    @SerializedName("price")
    val price : Double,
    @SerializedName("name")
    val name : String,
    @SerializedName("image")
    val image : String,
    @SerializedName("countInStock")
    val countInStock : String,
    @SerializedName("_id")
    val id : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("brand")
    val brand : String,
    @SerializedName("category")
    val category : String,
    @SerializedName("user")
    val user : String,
    @SerializedName("__v")
    val v : String,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("updatedAt")
    val updatedAt : String,
    @SerializedName("reviews")
    val reviews : List<InforReviewPerson>,
    var quality : Int
)