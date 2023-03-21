package com.example.myqrscanapp.Models


import com.google.gson.annotations.SerializedName

data class DetailsData(
    @SerializedName("detail1")
    var detail1: String,
    @SerializedName("detail2")
    var detail2: String,
    @SerializedName("detail3")
    var detail3: String
)