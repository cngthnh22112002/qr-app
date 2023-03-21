package com.example.myqrscanapp.Models


import com.google.gson.annotations.SerializedName

data class ResponseListProducts(
    @SerializedName("details")
    var details: DetailsData,
    @SerializedName("name")
    var name: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("price")
    var price: String,
)