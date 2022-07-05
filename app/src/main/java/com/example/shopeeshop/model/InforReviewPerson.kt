package com.example.shopeeshop.model

import com.google.gson.annotations.SerializedName

data class InforReviewPerson(
    @SerializedName("_id")
    val id : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("rating")
    val rating : Int,
    @SerializedName("comment")
    val comment : String,
    @SerializedName("user")
    val user : String,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("updatedAt")
    val updatedAt : String
)
